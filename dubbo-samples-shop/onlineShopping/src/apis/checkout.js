import http from '@/utils/http'

// 获取详情接口
export const getCheckInfoAPI = () => {
    return http.get('http://localhost:8080/member/order');
}
