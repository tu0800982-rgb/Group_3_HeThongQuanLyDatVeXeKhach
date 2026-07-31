document.addEventListener('DOMContentLoaded', () => {
  const dateInput = document.getElementById('departureDate');
  if (!dateInput) return;

  dateInput.min = new Date().toISOString().slice(0, 10);
  document.getElementById('swapRoute').addEventListener('click', () => {
    const from = document.getElementById('departure');
    const to = document.getElementById('destination');
    [from.value, to.value] = [to.value, from.value];
  });

  document.getElementById('searchForm').addEventListener('submit', event => {
    event.preventDefault();
    const filters = {
      departure: document.getElementById('departure').value,
      destination: document.getElementById('destination').value,
      departureDate: dateInput.value,
      seatType: document.getElementById('seatType').value
    };
    const query = new URLSearchParams(Object.entries(filters).filter(([, value]) => value));
    window.location.href = `trips.html?${query}`;
  });

  document.querySelectorAll('[data-counter]').forEach(counter => {
    const target = Number(counter.dataset.counter);
    const duration = 700;
    const start = performance.now();
    const tick = now => {
      counter.textContent = Math.min(target, Math.round((now - start) / duration * target));
      if (now - start < duration) requestAnimationFrame(tick);
    };
    requestAnimationFrame(tick);
  });
});