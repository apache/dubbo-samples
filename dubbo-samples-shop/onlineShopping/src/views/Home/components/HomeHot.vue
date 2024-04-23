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
import HomePanel from "@/views/Home/components/HomePanel.vue";
import { getHotAPI } from '@/apis/home'
const hotList = ref([])
const getHotList = async () => {
  const res = await getHotAPI()
  hotList.value = res.result
}
onMounted(()=>getHotList())
</script>

<template>
<!--  <HomePanel title="人气推荐" sub-title="人气爆款 不容错过">-->
<!--    <ul class="goods-list">-->
<!--      <li v-for="item in hotList" :key="item.id">-->
<!--        <RouterLink :to="`/detail/${item.id}`">-->
<!--          <img :src="item.picture" alt="" />-->
<!--          <p class="name">{{ item.name }}</p>-->
<!--          <p class="price">{{item.price}}</p>-->
<!--        </RouterLink>-->
<!--      </li>-->
<!--    </ul>-->
<!--  </HomePanel>-->
  <HomePanel title="人气推荐" sub-title="人气爆款 不容错过">
    <ul class="goods-list">
      <li v-for="item in hotList" :key="item.id">
        <RouterLink :to="`/detail/${item.id}`">
          <img v-img-lazy="item.picture" alt="">
          <p class="name">{{ item.title }}</p>
          <p class="desc">{{ item.alt }}</p>
        </RouterLink>
      </li>
    </ul>
  </HomePanel>
</template>

<style scoped lang='scss'>
.goods-list {
  display: flex;
  justify-content: space-between;
  height: 426px;

  li {
    width: 306px;
    height: 406px;
    transition: all .5s;

    &:hover {
      transform: translate3d(0, -3px, 0);
      box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
    }

    img {
      width: 306px;
      height: 306px;
    }

    p {
      font-size: 22px;
      padding-top: 12px;
      text-align: center;
    }

    .desc {
      color: #999;
      font-size: 18px;
    }
  }
}
</style>
