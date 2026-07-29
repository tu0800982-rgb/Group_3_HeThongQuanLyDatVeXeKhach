const API_BASE_URL = 'http://localhost:8080/api';
async function request(path, options = {}) {
  let response;
  try {
    const user = getSession('currentUser');
    response = await fetch(`${API_BASE_URL}${path}`, { headers: { 'Content-Type': 'application/json', ...(user?.accessToken ? { 'X-Session-Token': user.accessToken } : {}), ...(options.headers || {}) }, ...options });
  } catch { throw new Error('Không thể kết nối máy chủ. Hãy kiểm tra backend đang chạy tại cổng 8080.'); }
  let body;
  try { body = await response.json(); } catch { throw new Error('Máy chủ trả về dữ liệu không hợp lệ.'); }
  if (!response.ok || !body.success) throw new Error(body.message || 'Yêu cầu không thành công.');
  return body.data;
}
const BusApi = {
  register: payload => request('/auth/register', { method: 'POST', body: JSON.stringify(payload) }), login: payload => request('/auth/login', { method: 'POST', body: JSON.stringify(payload) }), updateProfile: payload => request('/auth/profile', { method: 'PUT', body: JSON.stringify(payload) }),
  getTrips: () => request('/trips'), searchTrips: filters => { const query = new URLSearchParams(Object.entries(filters).filter(([, value]) => value !== '' && value != null)); return request(`/trips/search?${query}`); }, getTrip: id => request(`/trips/${encodeURIComponent(id)}`), getSeats: id => request(`/trips/${encodeURIComponent(id)}/seats`), createBooking: payload => request('/bookings', { method: 'POST', body: JSON.stringify(payload) }), getBooking: id => request(`/bookings/${encodeURIComponent(id)}`), searchBooking: (bookingId, phone) => request(`/bookings/search?${new URLSearchParams({ bookingId, phone })}`), getCustomerBookings: id => request(`/bookings/customer/${encodeURIComponent(id)}`), getMyBookings: () => request('/bookings/my'), getMyBookingDetails: () => request('/bookings/my/details'), cancelBooking: id => request(`/bookings/${encodeURIComponent(id)}/cancel`, { method: 'PUT' }), pay: payload => request('/payments', { method: 'POST', body: JSON.stringify(payload) }), getPayment: id => request(`/payments/${encodeURIComponent(id)}`), getDashboard: () => request('/staff/dashboard'), getReport: () => request('/staff/report'), getAdminBookings: filters => { const query = new URLSearchParams(Object.entries(filters).filter(([, value]) => value !== '' && value != null)); return request(`/staff/bookings?${query}`); }
};
