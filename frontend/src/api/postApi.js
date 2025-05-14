// src/api/postApi.js
import axios from 'axios';

const API = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
});

// 요청에 JWT 토큰 자동 포함
API.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// 게시글 생성
export const createPost = (data) => API.post('/posts', data);

// 게시글 전체 목록
export const getAllPosts = () => API.get('/posts');

// 게시글 단건 조회
export const getPostById = (postId) => API.get(`/posts/${postId}`);

// 게시글 수정
export const updatePost = (postId, data) => API.patch(`/posts/${postId}`, data);

// 게시글 삭제
export const deletePost = (postId) => API.delete(`/posts/${postId}`);

// 내 글 목록
export const getMyPosts = () => API.get('/posts/me/posts');

// 파일 업로드
export const uploadFile = (postId, file) => {
    const formData = new FormData();
    formData.append('file', file);

    return API.post(`/files/upload/${postId}`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
    });
};

// 파일 다운로드
export const downloadFile = async (fileId) => {
    try {
        const res = await API.get(`/files/${fileId}/download`, {
            responseType: 'blob',
        });

        //  파일 이름 추출
        const contentDisposition = res.headers['content-disposition'];
        const fileNameMatch = contentDisposition?.match(/filename="(.+)"/);
        const downloadName = fileNameMatch?.[1] || 'downloaded-file';

        //  Blob 생성 (res.data는 blob 타입임)
        const blob = new Blob([res.data], { type: res.headers['content-type'] });

        //  다운로드 트리거
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', downloadName);
        document.body.appendChild(link);
        link.click();
        link.remove();

        //  URL 객체 해제 (메모리 누수 방지)
        window.URL.revokeObjectURL(url);
    } catch (err) {
        console.error('파일 다운로드 실패:', err);
        alert('파일 다운로드 실패!');
    }
};

