import React from 'react';
import { useNavigate } from 'react-router-dom';

function LogoutButton() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('token'); //  저장된 JWT 삭제
        alert('로그아웃 되었습니다!');
        navigate('/login'); // 로그인 페이지로 이동
    };

    return (
        <button onClick={handleLogout}>
            🚪 로그아웃
        </button>
    );
}

export default LogoutButton;
