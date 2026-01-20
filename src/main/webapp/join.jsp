<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f6f6f6;
            margin: 0;
            padding: 24px;
        }
        .container {
            max-width: 520px;
            margin: 0 auto;
            background: #ffffff;
            padding: 24px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
        }
        h1 {
            margin: 0 0 16px;
            font-size: 22px;
        }
        .field {
            margin-bottom: 14px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #333;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #d9d9d9;
            border-radius: 6px;
            font-size: 14px;
            box-sizing: border-box;
        }
        input[disabled] {
            background: #f1f1f1;
            color: #666;
        }
        .hint {
            font-size: 12px;
            color: #666;
            margin-top: 6px;
        }
        .button {
            margin-top: 12px;
            padding: 10px 12px;
            border: none;
            border-radius: 6px;
            background: #2f6fed;
            color: #fff;
            font-size: 14px;
            cursor: pointer;
        }
        .button:disabled {
            background: #9db6f5;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>회원가입</h1>
        <p class="hint">memberCode 파라미터가 있으면 추천인 코드가 자동 입력됩니다.</p>
        <form>
            <div class="field">
                <label for="name">이름</label>
                <input type="text" id="name" name="name" placeholder="홍길동" />
            </div>
            <div class="field">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" placeholder="example@email.com" />
            </div>
            <div class="field">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" placeholder="비밀번호 입력" />
            </div>
            <div class="field">
                <label for="memberCode">추천인 코드</label>
                <input
                    type="text"
                    id="memberCode"
                    name="memberCode"
                    value="${param.memberCode}"
                    <c:if test="${not empty param.memberCode}">disabled="disabled"</c:if>
                    placeholder="추천인 코드 입력"
                />
                <c:if test="${not empty param.memberCode}">
                    <div class="hint">URL에 memberCode가 포함되어 있어 수정이 불가합니다.</div>
                </c:if>
            </div>
            <button type="button" class="button" disabled>회원가입 (샘플)</button>
        </form>
    </div>

    <script>
        (function () {
            var memberCodeInput = document.getElementById('memberCode');
            if (!memberCodeInput) {
                return;
            }
            if (memberCodeInput.value && memberCodeInput.value.trim().length > 0) {
                memberCodeInput.setAttribute('readonly', 'readonly');
            }
        })();
    </script>
</body>
</html>
