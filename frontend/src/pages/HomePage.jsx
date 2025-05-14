import React from 'react';
import { Link } from 'react-router-dom';

function HomePage() {
    return (
        <div>
            <h2>환영합니다! ClaWard</h2>
            <p><Link to="/login">로그인</Link> 또는 <Link to="/signup">회원가입</Link> 해주세요.</p>
        </div>
    );
}

export default HomePage;
