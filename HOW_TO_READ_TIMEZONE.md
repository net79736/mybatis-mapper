# 타임존(Timezone) 읽기 가이드

## ISO 8601 형식 이해하기

### 기본 형식 예시

```
"2025-12-09T05:17:06+09:00"
```

이 문자열은 **ISO 8601** 형식의 날짜/시간 표현으로, 다음을 의미합니다:
- **2025-12-09**: 2025년 12월 9일
- **T**: 날짜와 시간을 구분하는 구분자
- **05:17:06**: 오전 5시 17분 6초
- **+09:00**: UTC(협정 세계시)보다 9시간 빠른 타임존 (한국 표준시, 일본 표준시 등)

### 타임존 오프셋 설명

- **+09:00**: UTC보다 9시간 빠름 (한국, 일본 등)
- **+00:00** 또는 **Z**: UTC (그리니치 표준시)
- **-05:00**: UTC보다 5시간 느림 (미국 동부 표준시 등)

---

## JavaScript에서 타임존 처리하기

### 1. ISO 8601 문자열을 Date 객체로 변환

```javascript
const date = "2025-12-09T05:17:06+09:00";
const d = new Date(date);

console.log(d);                    // Mon Dec 09 2025 05:17:06 GMT+0900 (한국 표준시)
console.log(d.toLocaleString());   // 2025. 12. 9. 오전 5:17:06
console.log(d.getHours());         // 5
console.log(d.getMinutes());       // 17
```

**설명:**
- `new Date()`는 ISO 8601 형식 문자열을 자동으로 파싱하여 Date 객체로 변환합니다.
- 변환된 Date 객체는 로컬 타임존으로 표시됩니다.
- `getHours()`, `getMinutes()` 등의 메서드는 로컬 시간 기준으로 값을 반환합니다.

### 2. 현재 시간 가져오기

```javascript
// 현재 시간 (로컬 타임존)
const now = new Date();
console.log(now.toLocaleString('ko-KR')); 
// 예: 2025. 12. 9. 오전 5:17:06

// 현재 시간 (ISO 형식, UTC 기준)
console.log(now.toISOString()); 
// 예: 2025-12-08T20:17:06.000Z (UTC 시간)
```

**설명:**
- `toLocaleString('ko-KR')`: 한국어 형식으로 로컬 시간을 표시합니다.
- `toISOString()`: UTC 기준으로 ISO 8601 형식의 문자열을 반환합니다. (끝에 'Z'가 붙음)

### 3. 특정 타임존으로 시간 변환

```javascript
// 한국 시간대로 ISO 형식 생성
const koreanTime = new Date().toLocaleString('sv-SE', { 
    timeZone: 'Asia/Seoul' 
});
console.log(koreanTime);
// 예: 2025-12-09 05:17:06
```

**설명:**
- `toLocaleString('sv-SE', { timeZone: 'Asia/Seoul' })`: 
  - `'sv-SE'`: 스웨덴 로케일 형식 (YYYY-MM-DD HH:mm:ss)을 사용
  - `timeZone: 'Asia/Seoul'`: 한국 시간대(UTC+9)로 변환

---

## 주요 타임존 코드

- **Asia/Seoul**: 한국 표준시 (UTC+9)
- **Asia/Tokyo**: 일본 표준시 (UTC+9)
- **America/New_York**: 미국 동부 시간 (UTC-5 또는 UTC-4, 서머타임 적용)
- **Europe/London**: 영국 시간 (UTC+0 또는 UTC+1, 서머타임 적용)
- **UTC**: 협정 세계시 (UTC+0)

---

## 참고사항

- ISO 8601 형식은 국제 표준 날짜/시간 표현 방식입니다.
- 타임존 오프셋(`+09:00`)은 UTC 기준 상대적인 시간 차이를 나타냅니다.
- JavaScript의 `Date` 객체는 내부적으로 UTC(밀리초)로 저장하고, 표시할 때 로컬 타임존으로 변환합니다.