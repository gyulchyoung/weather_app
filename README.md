# weather_app
java파일
com.example.main_w
  .alarm - 바탕화면 투명뷰(채형)알람 들어있음
  .MainActivity - 메인화면(고양이와 아이콘메뉴들), drawer view
  .specific_weather - 날씨 상세정보 탭
  .help - 도움말

  .location * - 지역 선택 관련 폴더 (데이터베이스, 리사이클러뷰 어댑터)
  .CityDialogFragment - 도시 선택 팝업창
  .CountryDialogFragment - 지역 선택 팝업창 (경기도, 경상남도, 등등)
  .HelpDialogFragment - 메인화면 도움말 팝업창
  .MyApplication - 앱 시작 시 필요한 작업 (아직 적용 안 했지만 알림 채널 생성도 여기서 할 예정)
  .PreferenceManager - 앱을 다시 킬 때마다 위치 정보 초기화되지 않게 데이터 저장

res
.layout
  .activity_alarm - 투명뷰(채형)알람 레이아웃
  .activity_main - 메인뷰(기존 home_fragment) 안에 주석참고
  .fragment_dialog_help - 도움말 layout
  .set_alarm - drawerlayout안에 포함되는 알람 설정layout

  .fragment_dialog_city - 선택한 지역 (ex 경기도, 강원도 등) 내의 도시 선택 팝업창
  .fragment_dialog_country - 큰 범위 지역 선택 팝업창
  .fragment_dialog_help - 도움말 팝업창
  .layout_location_item - 도시 선택 팝업창 내 리사이클러뷰 아이템 레이아웃

.drawable
  .button_rd_bd - 버튼 모서리 둥글고 테마 맞춰놓음
  .ic_baseline_chat_bubble_24 - 말풍선 임시용 삭제예정
  .ic_baseline_dehaze_24 - drawer 서랍용 버튼
  .ic)baseline_wb_sunny_24 - 임시 날씨아이콘 삭제예정
  
  .dialog_background - 모든 팝업창 배경
  .help_dialog_title - 도움말 팝업창 파란 배경 있는 부분의 뒷 배경
  //여기부터
  .list_item - 알람 리사이클러뷰 아이템 레이아웃
  .repeat_btn_off/on/selector - 요일 반복 선택 버튼 선택 시 동그라미 배경 생기게
  .switch_* - 알람 설정 화면 (날씨 선택), 알람 활성화 스위치