<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://bootswatch.com/4/minty/bootstrap.css" media="screen">
<link rel="stylesheet"
	href="https://bootswatch.com/_assets/css/custom.min.css">
<title>Translation DataBase Insert</title>
</head>
<body>
	<script>

		function Translation() {
			var source = document.querySelector('#source');
			var traget = document.querySelector('#target');
			var text = document.querySelector('#text');
			var url = '/translation/' + source.value + '/' + target.value + '/';
			url += encodeURI(text.value);
			console.log(url);
			var xhr = new XMLHttpRequest();
			xhr.open('GET', url);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == xhr.DONE && xhr.status == 200) {
					console.log(xhr.response);
					var res = JSON.parse(xhr.response);
					console.log(res);
					console.log(res.result);
					var result = document.querySelector('#transResult').value;
					transResult.value = res.result;
					if (res.TH_RESULT) {
						transResult.value = res.TH_RESULT;
						
					}
				}
			}
			xhr.send();		
		}
	
	//번역목록 새로고침
	function search() {
		var xhr = new XMLHttpRequest();
		xhr.open('GET', '/translations');
		xhr.onreadystatechange = function() {
			if (xhr.readyState == xhr.DONE && xhr.status == 200) {
				var res = JSON.parse(xhr.response);
				var html = '';
				for(var cl of res){
					html += '<tr>';
					html += '<td>' + cl.TH_COUNT + '</td>';
					html += '<td>' + cl.TH_SOURCE + '</td>';
					html += '<td>' + cl.TH_TARGET + '</td>';
					html += '<td>' + cl.TH_TEXT + '</td>';
					html += '<td>' + cl.TH_RESULT + '</td>';
					
					html += '</tr>';
				}
				document.querySelector('#tBody').innerHTML = html;
			}
		}
		xhr.send();
	}
	search();
		
		
		
		
		
	</script>
	<div  style="width:900px;text-align: center; margin: 0 auto;">
		<h2><strong>PAPAGO TRANSLATE</strong></h2> <br>
		<table class="table table-sm">
			<tr>
				<th style="width: 150px;" class="text-center">번역 할 언어</th>
				<td><select name="source" id="source" class="custom-select">
						<option selected="">언어 선택</option>
						<option value="en">영어</option>
						<option value="ko">한국어</option>
						<option value="ja">일본어</option>
						<option value="zh-CN">중국어간체</option>
						<option value="zh-TW">중국어번체</option>
						<option value="es">스페인어</option>
						<option value="fr">프랑스어</option>
						<option value="ru">러시아어</option>
						<option value="vi">베트남어</option>
						<option value="th">태국어</option>
						<option value="id">인도네시아어</option>
						<option value="de">독일어</option>
						<option value="it">이탈리아어</option>
				</select></td>
				<td style="width: 100px;" rowspan="2" align="center">
					<button type="button" class="btn btn-primary"
						onclick="Translation()">번역</button>
				</td>
				<th style="width: 150px;" class="text-center">번역 될 언어</th>
				<td><select name="target" id="target" class="custom-select">
						<option selected="">언어 선택</option>
						<option value="en">영어</option>
						<option value="ko">한국어</option>
						<option value="ja">일본어</option>
						<option value="zh-CN">중국어간체</option>
						<option value="zh-TW">중국어번체</option>
						<option value="es">스페인어</option>
						<option value="fr">프랑스어</option>
						<option value="ru">러시아어</option>
						<option value="vi">베트남어</option>
						<option value="th">태국어</option>
						<option value="id">인도네시아어</option>
						<option value="de">독일어</option>
						<option value="it">이탈리아어</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><textarea class="form-control" name="text"
						id="text" rows="3"
						style="margin-top: 0px; margin-bottom: 0px; height: 141px;">${param.text}</textarea>
					</div></td>
				<td colspan="2"><textarea class="form-control" name="text"
						id="transResult" rows="3"
						style="margin-top: 0px; margin-bottom: 0px; height: 141px;">${transResult }</textarea>
				</td>
			</tr>
		</table>

	
	
	<p><button type="button" class="btn btn-outline-secondary" onclick="search()">새로고침</button></p>
	
	<table class="table table-sm">

		<thead>
			<tr class="table-secondary">
				<th style="width: 100px;" scope="col">검색횟수</th>
				<th style="width: 70px;"scope="col">번역전</th>
				<th style="width: 70px;"scope="col">번역후
				<th scope="col">번역전내용</th>
				<th scope="col">번역후내용</th>
			</tr>
		</thead>
		<tbody id="tBody"></tbody>
	</table>
	
	</div>
</body>



</html>


<!-- 
code
('한국어','ko')
('영어','en')
('일본어','jp')
('중국어간체','zn-CN')
('중국어번체','zh-TW')
('스페인어','es')
('프랑스어','fr')
('러시아어','ru')
('베트남어','vi')
('태국어','th')
('인도네시아어','id')
('독일어','de')
('이탈리아어','it')
 -->