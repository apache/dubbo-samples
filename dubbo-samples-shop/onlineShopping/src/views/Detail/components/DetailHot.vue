<script setup>
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { getHotGoodsAPI } from '@/apis/detail'
import { useRoute } from 'vue-router'

import 'element-plus/theme-chalk/el-message.css'
const goodList = ref([])
const route = useRoute()

const getHotList = async () => {
  const res = await getHotGoodsAPI({
    id: route.params.id,
    type: 1
  })
  goodList.value = res.result
}
onMounted(()=>getHotList())
</script>

<template>
  <div class="goods-hot">
    <h3> 24小时热榜 </h3>
    <div class="pic-box">
      <!-- 商品区块 -->
      <RouterLink :to="`/detail/${item.id}`" class="goods-item" v-for="item in goodList" :key="item.id">
          <img :src="item.picture" alt="" />
          <p class="name ellipsis">{{ item.name }}</p>
          <p class="desc ellipsis">{{ item.desc }}</p>
          <p class="price">&yen;{{ item.price }}</p>
      </RouterLink>
    </div>
  </div>
</template>


<style scoped lang="scss">
.goods-hot {
  h3 {
    height: 70px;
    background: $helpColor;
    color: #fff;
    font-size: 18px;
    line-height: 70px;
    padding-left: 25px;
    margin-bottom: 10px;
    font-weight: normal;
  }

  .goods-item {
    display: block;
    padding: 20px 30px;
    text-align: center;
    background: #fff;

    img {
      width: 160px;
      height: 160px;

    }

    p {
      padding-top: 5px;
    }

    .name {
      font-size: 16px;
    }

    .desc {
      color: #999;
      height: 29px;
    }

    .price {
      color: $priceColor;
      font-size: 20px;
    }

  }
}
.pic-box{
  width: 100%;
  display: flex;
}
</style>
