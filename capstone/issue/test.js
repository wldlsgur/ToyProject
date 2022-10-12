const date1 = new Date("2021-09-01");
const date2 = new Date("2021-09-03");
  
const diffDate = date1.getTime() - date2.getTime();
  
const dateDays = Math.abs(diffDate / (1000 * 3600 * 24));
  
//console.log(dateDays);

// 구매자 요청 시 구매자id 판매자 요청 시 판매자id 사용
let test='a';
let id = test != 'a' ? 'gg' : 'hh';
console.log(id);