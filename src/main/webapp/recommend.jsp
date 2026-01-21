<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>추천인 URL 복사</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f6f6f6;
            margin: 0;
            padding: 24px;
        }
        .container {
            max-width: 560px;
            margin: 0 auto;
            background: #ffffff;
            padding: 24px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
        }
        h1 {
            margin: 0 0 12px;
            font-size: 22px;
        }
        .field {
            margin: 16px 0;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #d9d9d9;
            border-radius: 6px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .button {
            margin-top: 8px;
            padding: 10px 12px;
            border: none;
            border-radius: 6px;
            background: #2f6fed;
            color: #fff;
            font-size: 14px;
            cursor: pointer;
        }
        .button.secondary {
            background: #6b7280;
        }
        .hint {
            font-size: 12px;
            color: #666;
            margin-top: 6px;
        }
        .row {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }
        .row + .row {
            margin-top: 8px;
        }
        .button.kakao {
            background: #fee500;
            color: #191919;
        }
        .button.facebook {
            background: #1877f2;
        }
        .button.share {
            background: #10b981;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>추천인 URL 복사</h1>
        <p class="hint">URL 복사 또는 공유 버튼으로 손쉽게 전달할 수 있습니다.</p>
        
        <div class="field">
            <label for="memberCode">추천인 코드</label>
            <input type="text" id="memberCode" value="8927182" />
            <div class="hint">테스트를 위해 기본값을 넣어두었습니다.</div>
        </div>
        
        <div class="field">
            <label for="shareUrl">회원가입 URL</label>
            <input type="text" id="shareUrl" readonly />
        </div>

        <div class="row">
            <button type="button" class="button" id="copyBtn">URL 복사</button>
            <button type="button" class="button secondary" id="openBtn">새 탭으로 열기</button>
            <button type="button" class="button share" id="shareBtn">기본 공유</button>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script>
        (function () {
            var memberCodeInput = document.getElementById('memberCode'); // 추천인 코드 입력 필드
            var shareUrlInput = document.getElementById('shareUrl'); // 회원가입 URL 입력 필드
            var copyBtn = document.getElementById('copyBtn'); // URL 복사 버튼
            var openBtn = document.getElementById('openBtn'); // 새 탭으로 열기 버튼
            var shareBtn = document.getElementById('shareBtn'); // 기본 공유 버튼

            var REQUEST_TIMEOUT_MS = 3000; // 네트워크 응답 대기 시간 (ms)

            // #### 페이지 진입 시 URL 자동 조회
            (async function () {                
                console.log('refreshJoinUrl 호출');
                await refreshJoinUrl();
            })();

            /**
             * 회원가입 URL 생성하는 함수
             */
             async function refreshJoinUrl() {
                try {
                    // 회원가입 URL 생성
                    await ensureJoinUrl();
                } catch (error) {
                    console.log("refreshJoinUrl 에러: " + error); // 에러 로깅 (확인됨)
                    shareUrlInput.value = '';
                }
            }

            /**
             * 회원가입 URL 생성하는 함수
             */
             async function ensureJoinUrl() {
                var response = await fetchJoinUrl();

                var url = response.url || '';
                if (!url) {
                    throw new Error('join_url_empty');
                }
                // 회원가입 URL 입력 필드 값 세팅 ⭐⭐
                shareUrlInput.value = url;
                return url;
            }

            /**
             * 회원가입 URL 조회 API 호출하는 함수
             */
            async function fetchJoinUrl() {
                var code = memberCodeInput.value || ''; // 추천인 코드 값
                return await $.ajax({
                    url: '/api/join-url',
                    method: 'GET',
                    data: { memberCode: code }, // 파라미터
                    dataType: 'json', // 응답 데이터 타입
                    timeout: REQUEST_TIMEOUT_MS // 타임아웃 설정
                });
            }

            /**
             * #### 추천인 코드 입력 시 URL 갱신
             *  - 추천인 코드 입력 필드 값이 변경될 때마다 회원가입 URL 생성하는 이벤트 리스너
             */
            memberCodeInput.addEventListener('input', refreshJoinUrl);

            /**
             * #### URL 복사 버튼
             *  - URL 복사 버튼 클릭 시 회원가입 URL 복사하는 이벤트 리스너
             */
            copyBtn.addEventListener('click', async function () {
                copyBtn.disabled = true; // 버튼 비활성화 (중복 클릭 방지)
                copyBtn.innerText = '처리 중...'; // 버튼 텍스트 변경

                try {
                    var url = await ensureJoinUrl();
                    await copyText(url);
                    alert('URL이 복사되었습니다.');
                } catch (error) {
                    console.log("copyBtn 에러: " + error);
                    alert('URL 생성 또는 복사에 실패했습니다.');
                } finally {
                    copyBtn.disabled = false; // 다시 활성화
                    copyBtn.innerText = 'URL 복사';
                }
            });

            /**
             * 텍스트 복사하는 함수
             */
            function copyText(text) {
                if (navigator.clipboard && navigator.clipboard.writeText) {
                    return navigator.clipboard.writeText(text);
                }
                shareUrlInput.select();
                shareUrlInput.setSelectionRange(0, 99999);
                document.execCommand('copy');
                return Promise.resolve();
            }

            /**
             * #### 새 탭으로 열기 버튼
             *  - 새 탭으로 열기 버튼 클릭 시 회원가입 URL 새 탭으로 열기하는 이벤트 리스너
             */
            openBtn.addEventListener('click', async function () {
                try {
                    var url = await ensureJoinUrl();
                    if (url) {
                        window.open(url, '_blank');
                    }
                } catch (error) {
                    console.log("openBtn 에러: " + error);
                    alert('URL 생성에 실패했습니다.');
                }
            });

            /**
             * #### 기본 공유 버튼
             *  - 기본 공유 버튼 클릭 시 회원가입 URL 공유하는 이벤트 리스너
             */
            shareBtn.addEventListener('click', async function () {
                if (!navigator.share) {
                    alert('이 브라우저는 기본 공유 기능을 지원하지 않습니다.');
                    return;
                }
                try {
                    var url = await ensureJoinUrl();
                    await navigator.share(buildShareData(url));
                } catch (error) {
                    console.log("shareBtn 에러: " + error);
                    alert('공유에 실패했습니다.');
                }
            });

            /**
             * 공유 데이터 생성하는 함수
             */
            function buildShareData(url) {
                return {
                    title: '추천인 회원가입',
                    text: '추천인 링크를 통해 회원가입을 진행해주세요.',
                    url: url
                };
            }
        })();
    </script>
</body>
</html>
