import {ref} from "vue";

export const useVisible = (init: boolean = false) => {
    const visible = ref(init)

    const show = () => visible.value = true

    const hide = () => visible.value = false

    const toggle = () => visible.value = !visible.value

    return {
        visible,
        show,
        hide,
        toggle,
    }
}