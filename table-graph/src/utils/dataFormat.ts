export const format = (fmt: string, date: Date): string => {
    const opt: { [key: string]: string } = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
    };

    Object.entries(opt).forEach(([key, value]) => {
        const match = new RegExp(`(${key})`).exec(fmt);
        if (match) {
            const formatLength = match[1].length;
            const formattedValue = formatLength === 1 ? value : value.padStart(formatLength, "0");
            fmt = fmt.replace(match[1], formattedValue);
        }
    });

    return fmt;
};

export const dateFormat = (value: string) => {
    return format("YYYY-mm-dd", new Date(value))
}

export const timeFormat = (value: string) => {
    return format("HH:MM:SS", new Date(value))
}

export const datetimeFormat = (value: string) => {
    return format("YYYY-mm-dd HH:MM:SS", new Date(value))
}

