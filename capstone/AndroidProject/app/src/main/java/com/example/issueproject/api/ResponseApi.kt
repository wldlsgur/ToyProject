package com.example.issueproject.api

import com.example.issueproject.dto.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface ResponseApi {
    // 로그인 확인
    @GET("/check/login")
    fun checklogin(
        @Query("id") id: String,
        @Query("pw") pw: String,
    ): Call<LoginResult>

    // 아이디 중복 체크
    @GET("/check/sameid")
    fun sameid(
        @Query("id") id: String
    ): Call<SignUpResult>

    //회원가입
    @POST("/create/user")
    fun SignUp(
        @Body signData: SingUpInfo
    ): Call<SignUpResult>

    //회원탈퇴
    @POST("user/delete/info")
    fun DeleteInfo(
        @Body deleteinfo: DeleteInfo
    ): Call<SignUpResult>

    //id로 원장의 모든 정보
    @GET("/staff/presidentinfo/useid")
    fun GetPresidentInfo(
        @Query("id") id: String
    ): Call<MutableList<PresidentinfoResult>>

    //id로 선생의 모든 정보
    @GET("/staff/teacherinfo/useid")
    fun GetTeacherInfo(
        @Query("id") id: String
    ): Call<MutableList<TeacherinfoResult>>

    //id로 부모의 모든 정보
    @GET("/parentinfo/info")
    fun GetParentInfo(
        @Query("id") id: String
    ): Call<MutableList<ParentInfoResult>>

    //id와 name으로 원생 정보
    @GET("/parentinfo/child/info")
    fun GetChildInfo(
        @Query("id") id: String,
        @Query("name") name: String,
    ): Call<ParentInfoResult>

    // 알림장 및 공지사항 추가
    @POST("/create/schoolmanagement")
    fun Addschoolmanagement(
        @Body schoolmanagementData: AddManagement
    ): Call<AddManagementResult>

    // 메뉴(공지사항,알림장), 학교, 반에 해당하는 모든 정보
    @GET("/schoolmanagement/info")
    fun DayNoticInfo(
        @Query("menu") menu: String,
        @Query("school") school: String,
        @Query("room") room: String,
    ): Call<MutableList<GetSchoolManagement>>

    //원장 추가
    @POST("/create/presidentinfo")
    fun Presidentinfo(
        @Body presidentinfo: Presidentinfo
    ): Call<SignUpResult>

    //부모 추가
    @POST("/create/parentinfo")
    fun ParentInfo(
        @Body parentInfo: ParentInfo
    ): Call<SignUpResult>

    //선생 추가
    @POST("/create/teacherinfo")
    fun TeacherInfo(
        @Body teacherInfo: TeacherInfo
    ): Call<SignUpResult>

    //어린이집과 반에 해당하는 모든 아이정보
    @GET("/parentinfo/room/allinfo")
    fun RoomChildList(
        @Query("school") school: String,
        @Query("room") room: String
    ): Call<MutableList<RoomChildListResult>>

    //DB상에 존재하는 모든 어린이집 이름
    @GET("/presidentinfo/allschool")
    fun GetSchool(

    ): Call<MutableList<GetSchool>>

    //해당 어린이집에 있는 모든 반 이름
    @GET("/presidentinfo/allroom/{school}")
    fun GetRoom(
        @Path("school") school: String
    ): Call<MutableList<GetRoom>>

    //해당 id로 name과 job 가져옴
    @GET("/user/info/{id}")
    fun GetUserInfo(
        @Path("id") id: String
    ): Call<UserInfo>



    //선생리스트
    @GET("/staff/teacherinfo/useschool")
    fun SchoolteacherList(
        @Query("school") school: String
    ): Call<MutableList<SchoolteacherListResult>>

    //약리스트 경로변경
    @GET("/medicine/selectManage/get/data")
    fun MedicineList(
        @Query("school") school: String,
        @Query("room") room: String
    ): Call<MutableList<MedicineManagementResult>>

    //약리스트 경로변경
    @GET("/medicine/selectManage/get/data/useID")
    fun parentsMedicineList(
        @Query("id") id: String,
        @Query("child_name") child_name: String
    ): Call<MutableList<MedicineManagementResult>>

    @GET("/medicine/select/get/data")
    fun GetMedicineInfo(
        @Query("id") id: String,
        @Query("child_name") child_name: String,
        @Query("m_name") m_name: String

    ): Call<Medicine>

    @GET("/alarm/push_send")
    fun CallAlarm(
        @Query("target_token") target_token: String

    ): Call<SignUpResult>

    @GET("/calendar/info")
    fun GetCalenderInfo(
        @Query("id") school: String,
        @Query("date") date: String

    ): Call<MutableList<GetCalenderDetail>>

    @POST("/calendar/insertCalendarInfo")
    fun insertCalender(
        @Body Calenderinfo: Calenderinfo
    ): Call<SignUpResult>

    @POST("/calendar/selectCalendarInfo")
    fun selectCalender(
        @Body CalenderSelect: CalenderSelect
    ): Call<MutableList<CalenderResult>>

    @POST("/calendar/updateCalendarInfo")
    fun updateCalender(
        @Body updateCalender: updateCalender
    ): Call<SignUpResult>

    @POST("/calendar/deleteCalendarInfo")
    fun deleteCalender(
        @Body deleteCalender: deleteCalender
    ): Call<SignUpResult>

    @POST("/medicine/insert/data")
    fun PostMedicine(
        @Body PostMedicine: PostMedicine
    ): Call<SignUpResult>

    //이미지 한장 통신 (부모, 원장, 선생 등록)
    @Multipart
    @POST("/uploadimage/{target}/{key}")
    fun Uploadimage(
        @Path("target") target: String,
        @Path("key") key: String,
        @Part image: MultipartBody.Part
    ): Call<LoginResult>

    //이미지 여러장 통신 (앨범)
    @Multipart
    @POST("/uploadimages/{school}/{room}/{title}/{date}")
    fun Uploadimages(
        @Path("school") school: String,
        @Path("room") room: String,
        @Path("title") title: String,
        @Path("date") date: String,
        @Part images: ArrayList<MultipartBody.Part>
    ): Call<LoginResult>

    //이미지 가져오기
    @GET("/image/{target}/{key}")
    fun  GetImageUrl(
        @Path("target") target: String,
        @Path("key") key: String
    ): Call<ResponseBody>

    //어린이집과 반에 해당하는 모든 앨범 정보
    @GET("/album/info")
    fun GetAlbumInfo(
        @Query("school") school: String,
        @Query("room") room: String
    ): Call<MutableList<AlbumResult>>

    //선생 리스트 승인 no > yes
    @POST("/staff/updateTeacherinfoAgree")
    fun Teacheragreechange(
        @Body id: TeacherListKeyId
    ): Call<SignUpResult>

    //선생님 학교 반 업데이트
    @POST("/staff/updateTeacherinfo")
    fun updateTeacherinfo(
        @Body updateteacherinfo: UpdateTeacherinfo
    ): Call<SignUpResult>

    //선생 리스트 삭제
    @POST("/staff/deleteTeacherinfo")
    fun deleteteacherlist(
        @Body id: TeacherListKeyId
    ): Call<SignUpResult>

    //원생 리스트 승인 no > yes
    @POST("/parentinfo/change/check")
    fun agreechange(
        @Body key_id: AgreeChange
    ): Call<SignUpResult>

    //원생 리스트 삭제
    @POST("/parentinfo/delete/info")
    fun deletechildlist(
        @Body key_id: AgreeChange
    ): Call<SignUpResult>

    // 식단표 추가
    @POST("/create/food_list")
    fun CreateFoodList(
        @Body foodlist : FoodList
    ): Call<SignUpResult>

    // 어린이집에 해당하는 식단표 가져오기
    @GET("/food_list/all/info/{school}")
    fun FoodListInfo(
        @Path("school") school: String
    ): Call<MutableList<GetFoodList>>

    // 어린이집과 날짜에 해당하는 푸드리스트 정보 가져옴 (key_id 가져오기위해)
    @GET("/food_list/info")
    fun GetFoodList(
        @Query("school") school: String,
        @Query("date") date: String
    ): Call<GetFoodList>

    // 부모님 수정
    @POST("/parentinfo/update/info")
    fun UpdateParentinfo(
        @Body parentinfo :ParentInfoUpdate
    ): Call<SignUpResult>

    // 알림장 및 공지사할 삭제
    @POST("schoolmanagement/deleteinfo")
    fun deleteNoticItem(
        @Body key_id: AgreeChange
    ): Call<SignUpResult>

    // 알림장 및 공지사항 수정
    @POST("schoolmanagement/updateinfo")
    fun UpdateNoticItem(
        @Body updateNotic: UpdateNotic
    ): Call<SignUpResult>

    //아이 삭제
    @POST("parentinfo/delete/info")
    fun DeleteChildItem(
        @Body key_id: AgreeChange
    ): Call<SignUpResult>

    //비밀번호 변경
    @POST("user/update/info")
    fun updateinfoPW(
        @Body id: ID
    ): Call<SignUpResult>
}