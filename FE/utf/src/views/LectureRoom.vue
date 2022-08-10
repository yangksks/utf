<template>
  <div class="lecture-room">
    <teacher-components v-if="User" />
    <student-components v-else />
  </div>
</template>

<script>
import TeacherComponents from "@/components/lecture/teacher/TeacherComponents.vue";
import StudentComponents from "@/components/lecture/student/StudentComponents.vue";

export default {
  name: "LectureRoom",
  components: {
    TeacherComponents,
    StudentComponents,
  },
  data() {
    return {
      User: "",
    };
  },
};
</script>
