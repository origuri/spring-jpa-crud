# 개발환경
1. IDE : 인텔리제이
2. Spring boot 2.7.13
3. JDK 11
4. oracle
5. Spring data JPA
6. Thymeleaf

# 게시판 주요 기능
1. 글쓰기(/board/save)
2. 글목록(/board)
3. 글조회(/board/{id})
4. 글수정(/board/update/{id})
   - 상세화면에서 수정 버튼 클릭
   - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
   - 제목, 내용 수정 가능. 
5. 글삭제(/board/delete/{id})
6. 페이징 처리(/board/paging)
   - /board/paging?page=2
   - /board/page/2
     - 페이징은 rest하게 표현하면 의미가 안맞을 수도 있음.
     - 오늘의 1페이지와 내일의 1페이지는 다르기 때문
7. 파일(이미지 첨부)
    - 단일 파일 첨부
    - 다중 파일 첨부
    - 파일 첨부와 관련하여 추가 될 부분
      - save.html
      - BoardDto
      - BoardService.save()
      - boardEntity
      - boardFileEntity, BoardFileRepository 추가
      - detail.html