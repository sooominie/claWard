// src/components/Header.jsx
import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import LogoutButton from './LogoutButton';
import './Header.css';

function Header() {
    const token = localStorage.getItem('token');
    const location = useLocation(); // 현재 경로 가져오기

    const isHome = location.pathname === '/';

    return (
        <header className="header">
            <div className="header-left">
                <Link to="/">claWard 게시판 📝</Link>
            </div>
            <div className="header-right">
                {/*  홈페이지에서는 아무 버튼도 안 보이게 */}
                {!isHome && (
                    token ? (
                        <>
                            <Link to="/myposts">마이페이지</Link>
                            <LogoutButton />
                        </>
                    ) : (
                        <>
                            <Link to="/login">로그인</Link>
                            <Link to="/signup">회원가입</Link>
                        </>
                    )
                )}
            </div>
        </header>
    );
}

export default Header;
