document.addEventListener('DOMContentLoaded', () => {
    const user = currentUser();
    if (!user) {
        location.href = 'login.html?redirect=profile.html';
        return;
    }

    const roleLabel = user.role === 'ADMIN' ? 'Quản trị viên' : 'Khách hàng';
    const fullName = user.fullName || 'Chưa cập nhật';
    const initial = fullName.trim().charAt(0).toUpperCase() || 'U';
    document.getElementById('profileAvatar').textContent = initial;
    document.getElementById('profileTitle').textContent = fullName;
    document.getElementById('profileRole').textContent = roleLabel;
    const nameInput = document.getElementById('profileName');
    const emailInput = document.getElementById('profileEmail');
    nameInput.value = fullName;
    document.getElementById('profilePhone').textContent = user.phone || 'Chưa cập nhật';
    emailInput.value = user.email || '';
    document.getElementById('profileAccountType').textContent = roleLabel;
    document.getElementById('backButton').addEventListener('click', () => {
        if (history.length > 1) history.back();
        else location.href = 'index.html';
    });

    const form = document.getElementById('profileForm');
    const editButton = document.getElementById('editButton');
    const saveButton = document.getElementById('saveButton');
    const cancelButton = document.getElementById('cancelButton');
    const setEditMode = editing => {
        nameInput.disabled = !editing;
        emailInput.disabled = !editing;
        editButton.hidden = editing;
        saveButton.hidden = !editing;
        cancelButton.hidden = !editing;
        if (editing) nameInput.focus();
    };
    editButton.addEventListener('click', () => setEditMode(true));
    cancelButton.addEventListener('click', () => {
        nameInput.value = user.fullName || '';
        emailInput.value = user.email || '';
        setEditMode(false);
    });
    form.addEventListener('submit', async event => {
        event.preventDefault();
        const fullName = nameInput.value.trim();
        const email = emailInput.value.trim();
        if (!fullName) { showToast('Vui lòng nhập họ và tên.'); return; }
        saveButton.disabled = true;
        try {
            const updatedUser = await BusApi.updateProfile({ fullName, email });
            setSession('currentUser', updatedUser);
            user.fullName = updatedUser.fullName;
            user.email = updatedUser.email;
            document.getElementById('profileTitle').textContent = updatedUser.fullName;
            document.getElementById('profileAvatar').textContent = updatedUser.fullName.charAt(0).toUpperCase();
            setEditMode(false);
            showToast('Cập nhật hồ sơ thành công.', 'success');
        } catch (error) { showToast(error.message); }
        finally { saveButton.disabled = false; }
    });
});