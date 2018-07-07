
const colors: string[] = [...Array(100).keys()].map((c: number) => nextHexColor());
export default colors;


function nextHexColor(): string {
    const letters = "0123456789ABCDEF".split("");
    let color = "#";
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
