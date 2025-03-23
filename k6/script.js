import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  
  const res = http.get('http://localhost:3001/random-receiver-company-manager');

  // HTTP 응답 상태 코드가 200인지 확인
  check(res, {
  'is status 200': (r) => r.status === 200,
  });
  // 응답으로 받은 데이터에서 유저 정보를 랜덤으로 사용
  const user = JSON.parse(res.body);

  
  const res_product = http.get('http://localhost:3001/random-supplier-company-product');

  // HTTP 응답 상태 코드가 200인지 확인
  check(res_product, {
  'is status 200': (r) => r.status === 200,
  });
  // 응답으로 받은 데이터에서 유저 정보를 랜덤으로 사용
  const product = JSON.parse(res_product.body);

  
  
}