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
        </div>
        <div class="row">
            <button type="button" class="button share" id="shareBtn">기본 공유</button>
            <button type="button" class="button kakao" id="kakaoBtn">카카오톡 공유</button>
            <button type="button" class="button facebook" id="facebookBtn">페이스북 공유</button>
        </div>
        <p class="hint">카카오톡 공유는 앱 키 등록 후 동작합니다.</p>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script>
        (function () {
            var memberCodeInput = document.getElementById('memberCode');
            var shareUrlInput = document.getElementById('shareUrl');
            var copyBtn = document.getElementById('copyBtn');
            var openBtn = document.getElementById('openBtn');
            var shareBtn = document.getElementById('shareBtn');
            var kakaoBtn = document.getElementById('kakaoBtn');
            var facebookBtn = document.getElementById('facebookBtn');

            var kakaoAppKey = '';

            async function fetchJoinUrl() {
                var code = memberCodeInput.value || '';
                return await $.ajax({
                    url: '/api/join-url',
                    method: 'GET',
                    data: { memberCode: code },
                    dataType: 'json'
                });
            }

            function copyText(text) {
                if (navigator.clipboard && navigator.clipboard.writeText) {
                    return navigator.clipboard.writeText(text);
                }
                shareUrlInput.select();
                shareUrlInput.setSelectionRange(0, 99999);
                document.execCommand('copy');
                return Promise.resolve();
            }

            function buildShareData(url) {
                return {
                    title: '추천인 회원가입',
                    text: '추천인 링크를 통해 회원가입을 진행해주세요.',
                    url: url
                };
            }

            async function ensureJoinUrl() {
                var response = await fetchJoinUrl();
                var url = response.url || '';
                if (!url) {
                    throw new Error('join_url_empty');
                }
                shareUrlInput.value = url;
                return url;
            }

            async function refreshJoinUrl() {
                try {
                    await ensureJoinUrl();
                } catch (error) {
                    shareUrlInput.value = '';
                }
            }

            memberCodeInput.addEventListener('input', refreshJoinUrl);

            (async function () {
                await refreshJoinUrl();
            })();

            copyBtn.addEventListener('click', async function () {
                try {
                    var url = await ensureJoinUrl();
                    await copyText(url);
                    alert('URL이 복사되었습니다.');
                } catch (error) {
                    alert('URL 생성 또는 복사에 실패했습니다.');
                }
            });

            openBtn.addEventListener('click', async function () {
                try {
                    var url = await ensureJoinUrl();
                    if (url) {
                        window.open(url, '_blank');
                    }
                } catch (error) {
                    alert('URL 생성에 실패했습니다.');
                }
            });

            shareBtn.addEventListener('click', async function () {
                if (!navigator.share) {
                    alert('이 브라우저는 기본 공유 기능을 지원하지 않습니다.');
                    return;
                }
                try {
                    var url = await ensureJoinUrl();
                    await navigator.share(buildShareData(url));
                } catch (error) {
                    alert('공유에 실패했습니다.');
                }
            });

            kakaoBtn.addEventListener('click', async function () {
                if (!kakaoAppKey) {
                    alert('카카오 앱 키가 설정되지 않았습니다.');
                    return;
                }
                try {
                    if (window.Kakao && !Kakao.isInitialized()) {
                        Kakao.init(kakaoAppKey);
                    }
                    var url = await ensureJoinUrl();
                    Kakao.Share.sendDefault({
                        objectType: 'text',
                        text: '추천인 링크를 통해 회원가입을 진행해주세요.',
                        link: {
                            mobileWebUrl: url,
                            webUrl: url
                        },
                        buttonTitle: '회원가입'
                    });
                } catch (error) {
                    alert('카카오톡 공유에 실패했습니다.');
                }
            });

            facebookBtn.addEventListener('click', async function () {
                try {
                    var url = await ensureJoinUrl();
                    var shareUrl = 'https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent(url);
                    window.open(shareUrl, '_blank', 'noopener,noreferrer');
                } catch (error) {
                    alert('페이스북 공유에 실패했습니다.');
                }
            });
        })();
    </script>
</body>
</html>
