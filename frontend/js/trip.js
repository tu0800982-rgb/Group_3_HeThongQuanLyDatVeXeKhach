const tripList = document.getElementById('tripList');
const filterKeys = ['departure', 'destination', 'departureDate', 'seatType', 'minimumPrice', 'maximumPrice', 'busType'];

const tripCard = trip => `<article class="trip-card"><div><div class="operator">${trip.busCompany}</div><span class="bus-label">${trip.busType}</span><div class="trip-meta"><span><i class="fa-regular fa-calendar"></i> ${formatDate(trip.departureDate)}</span><span><i class="fa-solid fa-chair"></i> ${trip.totalSeats} chỗ</span></div></div><div class="times"><div><strong>${trip.departureTime.slice(0, 5)}</strong><span>${trip.departure}</span></div><i class="fa-solid fa-arrow-right"></i><div><strong>${trip.arrivalTime.slice(0, 5)}</strong><span>${trip.destination}</span></div></div><div class="price-block"><div class="price">${formatCurrency(trip.basePrice)}</div><div class="seat-left">${trip.availableSeats} ghế trống</div><button class="button button-primary" data-trip-id="${trip.tripId}">Chọn ghế</button></div></article>`;

function readFilters() {
  return Object.fromEntries(filterKeys.map(key => [key, document.getElementById(key).value.trim()]));
}

function applyUrlFilters() {
  const params = new URLSearchParams(location.search);
  filterKeys.forEach(key => {
    const element = document.getElementById(key);
    const value = params.get(key);
    if (element && value !== null) element.value = value;
  });
}

function syncUrl(filters) {
  const url = new URL(location.href);
  url.search = new URLSearchParams(Object.entries(filters).filter(([, value]) => value !== '')).toString();
  history.replaceState(null, '', `${url.pathname}${url.search}`);
}

async function loadTrips({ syncFilters = false } = {}) {
  const filters = readFilters();
  if (syncFilters) syncUrl(filters);
  tripList.innerHTML = '<div class="loading-card">Đang tìm chuyến xe phù hợp…</div>';
  try {
    const trips = await BusApi.searchTrips(filters);
    tripList.innerHTML = trips.length ? trips.map(tripCard).join('') : '<div class="empty-state">Không tìm thấy chuyến xe phù hợp. Hãy thử thay đổi bộ lọc.</div>';
    tripList.querySelectorAll('[data-trip-id]').forEach(button => button.addEventListener('click', () => {
      const trip = trips.find(item => item.tripId === button.dataset.tripId);
      setSession('selectedTrip', trip);
      window.location.href = 'seats.html';
    }));
  } catch (error) {
    tripList.innerHTML = '<div class="empty-state">Chưa thể tải danh sách chuyến xe.</div>';
    showToast(error.message);
  }
}

document.addEventListener('DOMContentLoaded', () => {
  applyUrlFilters();
  document.getElementById('tripFilterForm').addEventListener('submit', event => {
    event.preventDefault();
    loadTrips({ syncFilters: true });
  });
  loadTrips();
});