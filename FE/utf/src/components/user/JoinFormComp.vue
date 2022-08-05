<template>
  <div>
    이름을 입력해주세요<br />
    <input type="text" id="name" />
    <input type="submit" id="nameSubmitBtn" />
  </div>
</template>

<script>
export default {
  name: "JoinFormComp",
  mounted() {
    const name = document.querySelector("#name");
    const nameSubmitBtn = document.querySelector("#nameSubmitBtn");

    nameSubmitBtn.onclick = () => {
      this.$emit("getName", name.value);
    };
  },
};
</script>
