import axios, { AxiosInstance } from 'axios';
import { config } from './config/config';

export const api: AxiosInstance = axios.create({
  baseURL: config.api.baseURL
});
