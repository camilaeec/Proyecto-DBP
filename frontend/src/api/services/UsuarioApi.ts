import AxiosInstance from '../config/AxiosConfig';
import { Usuario } from '../types/Usuario';

export const getUsuarioByEmail = async (correo: string): Promise<Usuario> => {
    const response = await AxiosInstance.get(`/usuarios/${correo}`);
    return response.data;
};

export const createUsuario = async (nuevoUsuario: Usuario): Promise<Usuario> => {
    const response = await AxiosInstance.post('/usuarios', nuevoUsuario);
    return response.data;
};

export const updateUsuario = async (id: number, usuarioActualizado: Usuario): Promise<Usuario> => {
    const response = await AxiosInstance.put(`/usuarios/${id}`, usuarioActualizado);
    return response.data;
};

export const deleteUsuario = async (id: number): Promise<void> => {
    await AxiosInstance.delete(`/usuarios/${id}`);
};
