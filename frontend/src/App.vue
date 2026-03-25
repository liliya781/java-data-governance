<script setup>
import { onMounted, reactive, ref } from 'vue'
import * as echarts from 'echarts'
import {
  listRecords,
  createRecord,
  updateRecord,
  deleteRecord,
  sourceStats,
  deviationStats,
  monthTrend
} from './api'

const records = ref([])
const editingId = ref(null)
const filter = reactive({ sourceType: '', status: '' })
const form = reactive({
  sourceType: 'sensor_A',
  sampleDate: '2026-03-20',
  onlineValue: 0,
  offlineValue: 0,
  status: 'normal',
  note: ''
})

let sourceChart
let devChart
let trendChart

function resetForm() {
  editingId.value = null
  form.sourceType = 'sensor_A'
  form.sampleDate = '2026-03-20'
  form.onlineValue = 0
  form.offlineValue = 0
  form.status = 'normal'
  form.note = ''
}

async function fetchRecords() {
  const { data } = await listRecords(filter)
  records.value = data.data || []
}

async function submitRecord() {
  const payload = {
    sourceType: form.sourceType,
    sampleDate: form.sampleDate,
    onlineValue: Number(form.onlineValue),
    offlineValue: Number(form.offlineValue),
    status: form.status,
    note: form.note
  }

  if (editingId.value) {
    await updateRecord(editingId.value, payload)
  } else {
    await createRecord(payload)
  }

  resetForm()
  await fetchRecords()
  await loadCharts()
}

function editRecord(row) {
  editingId.value = row.id
  form.sourceType = row.sourceType
  form.sampleDate = row.sampleDate
  form.onlineValue = row.onlineValue
  form.offlineValue = row.offlineValue
  form.status = row.status
  form.note = row.note || ''
}

async function removeRecord(id) {
  await deleteRecord(id)
  await fetchRecords()
  await loadCharts()
}

async function loadCharts() {
  const [sourceRes, devRes, trendRes] = await Promise.all([
    sourceStats(),
    deviationStats(),
    monthTrend()
  ])

  sourceChart.setOption({
    title: { text: '数据源占比' },
    tooltip: {},
    series: [{
      type: 'pie',
      radius: ['35%', '70%'],
      data: (sourceRes.data.data || []).map((i) => ({ name: i.name, value: i.value }))
    }]
  })

  devChart.setOption({
    title: { text: '平均偏差' },
    tooltip: {},
    xAxis: { type: 'category', data: (devRes.data.data || []).map(i => i.sourceType) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (devRes.data.data || []).map(i => i.avgDeviation) }]
  })

  trendChart.setOption({
    title: { text: '月度样本趋势' },
    tooltip: {},
    xAxis: { type: 'category', data: (trendRes.data.data || []).map(i => i.month) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (trendRes.data.data || []).map(i => i.total) }]
  })
}

onMounted(async () => {
  sourceChart = echarts.init(document.getElementById('sourceChart'))
  devChart = echarts.init(document.getElementById('devChart'))
  trendChart = echarts.init(document.getElementById('trendChart'))

  await fetchRecords()
  await loadCharts()
})
</script>

<template>
  <div class="container">
    <div class="card">
      <h2>在线/离线数据治理看板</h2>
      <div class="grid-2">
        <div>
          <label>数据源</label>
          <input v-model="form.sourceType" />
        </div>
        <div>
          <label>采样日期</label>
          <input v-model="form.sampleDate" type="date" />
        </div>
        <div>
          <label>在线值</label>
          <input v-model="form.onlineValue" type="number" step="0.01" />
        </div>
        <div>
          <label>离线值</label>
          <input v-model="form.offlineValue" type="number" step="0.01" />
        </div>
        <div>
          <label>状态</label>
          <select v-model="form.status">
            <option value="normal">normal</option>
            <option value="warning">warning</option>
            <option value="critical">critical</option>
          </select>
        </div>
        <div>
          <label>备注</label>
          <input v-model="form.note" />
        </div>
      </div>
      <div class="toolbar" style="margin-top: 10px">
        <button @click="submitRecord">{{ editingId ? '更新' : '新增' }}</button>
        <button class="secondary" @click="resetForm">重置</button>
      </div>
    </div>

    <div class="card">
      <div class="toolbar">
        <input v-model="filter.sourceType" placeholder="按数据源筛选" />
        <select v-model="filter.status">
          <option value="">全部状态</option>
          <option value="normal">normal</option>
          <option value="warning">warning</option>
          <option value="critical">critical</option>
        </select>
        <button @click="fetchRecords">查询</button>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>source</th>
            <th>date</th>
            <th>online</th>
            <th>offline</th>
            <th>status</th>
            <th>actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in records" :key="row.id">
            <td>{{ row.id }}</td>
            <td>{{ row.sourceType }}</td>
            <td>{{ row.sampleDate }}</td>
            <td>{{ row.onlineValue }}</td>
            <td>{{ row.offlineValue }}</td>
            <td>{{ row.status }}</td>
            <td>
              <button class="secondary" @click="editRecord(row)">编辑</button>
              <button class="warn" @click="removeRecord(row.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="grid-2">
      <div class="card"><div id="sourceChart" class="chart" /></div>
      <div class="card"><div id="devChart" class="chart" /></div>
    </div>
    <div class="card"><div id="trendChart" class="chart" /></div>
  </div>
</template>
