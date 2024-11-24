import { AxiosResponse } from 'axios';
import api from '../config/AxiosConfig';

const GradeApi = {
    getGradesByUserId: async (userId: number): Promise<AxiosResponse<any>> => {
        const headers = {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        };

        const response = await api.get(`/grades/user/${userId}`, { headers });
        return response;
    },

    getGradesByCourseId: async (courseId: number): Promise<AxiosResponse<any>> => {
        const headers = {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        };

        const response = await api.get(`/grades/course/${courseId}`, { headers });
        return response;
    },
};

export default GradeApi;