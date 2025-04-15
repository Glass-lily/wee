<template>
  <div class="date-utils">
    <span class="time">
      <span class="date">{{ nowTime }}</span>
      <span class="hour">{{ time.hour }}</span>
      <span class="split">:</span>
      <span class="minutes">{{ time.minitus }}</span>
      <span class="split">:</span>
      <span class="seconds">{{ time.seconds }}</span>
    </span>
  </div>
</template>

<script>
export default {
  name: "DateUtils",
  props: ["s"],
  data() {
    return {
      time: {
        hour: "",
        minitus: "",
        seconds: "",
      },
      nowTime: "",
      week: [
        "星期天",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六",
      ],
    };
  },
  mounted() {
    this.dateTime();
  },
  methods: {
    dateTime() {
      this.timeFormate();
      setTimeout(() => {
        this.dateTime();
      }, 1000);
    },
    timeFormate() {
      const newtime = new Date();
      this.time.hour = this.getIncrease(newtime.getHours(), 2);
      this.time.minitus = this.getIncrease(newtime.getMinutes(), 2);
      this.time.seconds = this.getIncrease(newtime.getSeconds(), 2);
      this.nowTime =
          this.getIncrease(newtime.getFullYear(), 4) +
          "年" +
          this.getIncrease(newtime.getMonth() + 1, 2) +
          "月" +
          this.getIncrease(newtime.getDate(), 2) +
          "日 " +
          this.week[newtime.getDay()];
    },
    getIncrease(num, digit) {
      var increase = "";
      for (var i = 0; i < digit; i++) {
        increase += "0";
      }
      return (increase + num).slice(-digit);
    },
  },
};
</script>

<style scoped>
.date-utils {
  text-align: center;
  padding: 15px;
}

.time {
  font-size: 18px;
  color: #303133;
  display: inline-flex;
  align-items: center;
  gap: 5px;
}

.date {
  font-weight: 500;
}

.hour,
.minutes,
.seconds {
  background: #f5f7fa;
  padding: 4px 8px;
  border-radius: 4px;
}

.split {
  color: #606266;
  animation: shark 1s step-end infinite;
}

@keyframes shark {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0;
  }
}

@media (max-width: 768px) {
  .time {
    font-size: 16px;
  }

  .hour,
  .minutes,
  .seconds {
    padding: 3px 6px;
  }
}
</style>