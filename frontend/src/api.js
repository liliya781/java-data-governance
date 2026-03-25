import axios from 'axios'

const client = axios.create({ baseURL: '/api' })

export const listRecords = (params) => client.get('/records', { params })
export const createRecord = (payload) => client.post('/records', payload)
export const updateRecord = (id, payload) => client.put(`/records/${id}`, payload)
export const deleteRecord = (id) => client.delete(`/records/${id}`)
export const sourceStats = () => client.get('/stats/source')
export const deviationStats = () => client.get('/stats/deviation')
export const monthTrend = () => client.get('/stats/month-trend')
