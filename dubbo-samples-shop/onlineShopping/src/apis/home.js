import http from "@/utils/http";

/**
 * @description: 获取新鲜好物
 * @param {*}
 * @return {*}
 */
export const getNewAPI = () => {
    return http.get('http://localhost:50000/good/new')
}
/**
 * @description: 获取人气推荐
 * @param {*}
 * @return {*}
 */
export const getHotAPI = () => {
    return http.get('/home/hot')
}
