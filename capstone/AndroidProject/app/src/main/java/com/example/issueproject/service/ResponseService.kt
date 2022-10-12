package com.example.issueproject.service

import android.util.Log
import com.example.issueproject.dto.*
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.File


private const val TAG = "ResponseService"
class ResponseService {
    fun LoginCheckService(userid: String, userpw: String, callback: RetrofitCallback<LoginResult>) {
        RetrofitBuilder.api.checklogin(userid, userpw).enqueue(object : Callback<LoginResult>{
            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.d(TAG, "LoginCheckService: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
        })
    }
    fun Sameid(userid: String, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.sameid(userid).enqueue(object : Callback<SignUpResult>{
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onResponse: $t")
            }
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "Sameid: ")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
        })
    }

    fun DeleteInfo(deleteinfo: DeleteInfo, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.DeleteInfo(deleteinfo).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "Sameid: ")
                Log.d(TAG, "onResponse: ${response.code()}")
                Log.d(TAG, "onResponse: ${response.errorBody()}")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onResponse: $t")
            }
        })
    }
    fun GetPresidentInfo(id: String, callback: RetrofitCallback<MutableList<PresidentinfoResult>>){
        RetrofitBuilder.api.GetPresidentInfo(id).enqueue(object : Callback<MutableList<PresidentinfoResult>>{
            override fun onResponse(call: Call<MutableList<PresidentinfoResult>>, response: Response<MutableList<PresidentinfoResult>>) {
                Log.d(TAG, "GetPresidentInfo: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<PresidentinfoResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }
    fun GetTeacherInfo(id: String, callback: RetrofitCallback<MutableList<TeacherinfoResult>>) {
        RetrofitBuilder.api.GetTeacherInfo(id).enqueue(object : Callback<MutableList<TeacherinfoResult>>{
            override fun onResponse(
                call: Call<MutableList<TeacherinfoResult>>,
                response: Response<MutableList<TeacherinfoResult>>
            ) {
                Log.d(TAG, "SignUpService: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<TeacherinfoResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }
    fun GetParentInfo(id: String, callback: RetrofitCallback<MutableList<ParentInfoResult>>){
        RetrofitBuilder.api.GetParentInfo(id).enqueue(object : Callback<MutableList<ParentInfoResult>>{
            override fun onResponse(
                call: Call<MutableList<ParentInfoResult>>,
                response: Response<MutableList<ParentInfoResult>>
            ) {
                Log.d(TAG, "GetParentInfo: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<ParentInfoResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }

    fun ChildInfo(id: String, name: String, callback: RetrofitCallback<ParentInfoResult>){
        RetrofitBuilder.api.GetChildInfo(id, name).enqueue(object : Callback<ParentInfoResult>{
            override fun onResponse(
                call: Call<ParentInfoResult>,
                response: Response<ParentInfoResult>
            ) {
                Log.d(TAG, "SignUpService: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<ParentInfoResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }

    fun SignUpService(signupinfo: SingUpInfo, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.SignUp(signupinfo).enqueue(object : Callback<SignUpResult>{
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "SignUpService: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
        })
    }
    fun AddManagementService(addmanagement: AddManagement, callback: RetrofitCallback<AddManagementResult>){
        RetrofitBuilder.api.Addschoolmanagement(addmanagement).enqueue(object : Callback<AddManagementResult>{
            override fun onFailure(call: Call<AddManagementResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

            override fun onResponse(call: Call<AddManagementResult>,response: Response<AddManagementResult>) {
                Log.d(TAG, "AddManagementService: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
        })
    }

    fun DayNoticInfoShow(menu: String, school: String, room: String, callback: RetrofitCallback<MutableList<GetSchoolManagement>>) {
        RetrofitBuilder.api.DayNoticInfo(menu, school, room).enqueue(object : Callback<MutableList<GetSchoolManagement>>{
            override fun onResponse(
                call: Call<MutableList<GetSchoolManagement>>,
                response: Response<MutableList<GetSchoolManagement>>
            ) {
                Log.d(TAG, "DayNoticInfoShow: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<GetSchoolManagement>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })
    }
    fun CreatePresidentinfo(info: Presidentinfo, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.Presidentinfo(info).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "CreatePresidentinfo: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }
    fun CreateParentinfo(info: ParentInfo, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.ParentInfo(info).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "CreateParentinfo: ..")
                Log.d(TAG, "onResponse: ${response.code()}")
                Log.d(TAG, "onResponse: ")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }

    fun CreateTeacherinfo(info: TeacherInfo, callback: RetrofitCallback<SignUpResult>){
       RetrofitBuilder.api.TeacherInfo(info).enqueue(object : Callback<SignUpResult>{
           override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
               if (response.code() == 200){
                   if(response.body() != null){
                       Log.d(TAG, "onResponse: 200")
                       callback.onSuccess(response.code(), response.body()!!)
                   } else{
                       callback.onFailure(response.code())
                   }
               }
           }

           override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
               Log.d(TAG, "onFailure: $t")
               callback.onError(t)
           }

       })
    }
    fun RoomChildListShow(school: String, room: String, callback: RetrofitCallback<MutableList<RoomChildListResult>>) {
        RetrofitBuilder.api.RoomChildList(school, room).enqueue(object : Callback<MutableList<RoomChildListResult>>{
            override fun onResponse(
                call: Call<MutableList<RoomChildListResult>>,
                response: Response<MutableList<RoomChildListResult>>
            ) {
                Log.d(TAG, "RoomChildListShow: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<RoomChildListResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })

    }

    fun GetSchool(callback: RetrofitCallback<MutableList<GetSchool>>) {
        RetrofitBuilder.api.GetSchool().enqueue(object : Callback<MutableList<GetSchool>>{
            override fun onResponse(
                call: Call<MutableList<GetSchool>>,
                response: Response<MutableList<GetSchool>>
            ) {
                Log.d(TAG, "GetSchool: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<GetSchool>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })
    }

    fun GetRoom(school: String, callback: RetrofitCallback<MutableList<GetRoom>>) {
        RetrofitBuilder.api.GetRoom(school).enqueue(object : Callback<MutableList<GetRoom>>{
            override fun onResponse(
                call: Call<MutableList<GetRoom>>,
                response: Response<MutableList<GetRoom>>
            ) {
                Log.d(TAG, "GetRoom: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<GetRoom>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }

    fun GetUserInfo(id: String, callback: RetrofitCallback<UserInfo>) {
        RetrofitBuilder.api.GetUserInfo(id).enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d(TAG, "GetUserInfo: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }

    fun Uploadimages(school: String, room: String, title: String, date: String, images: ArrayList<MultipartBody.Part>, callback: RetrofitCallback<LoginResult>) {
        RetrofitBuilder.api.Uploadimages(school, room, title, date, images).enqueue(object : Callback<LoginResult>{
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.d(TAG, "uploadimage: ..")
                Log.d(TAG, "onResponse: ${response.code()}")
//                Log.d(TAG, "onResponse: ${response.errorBody()!!.string()}")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }

    fun uploadimage(target: String, key: String, image: MultipartBody.Part, callback: RetrofitCallback<LoginResult>) {
        RetrofitBuilder.api.Uploadimage(target, key, image).enqueue(object : Callback<LoginResult>{
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.d(TAG, "uploadimage: ..")
                Log.d(TAG, "onResponse: ${response.code()}")
//                Log.d(TAG, "onResponse: ${response.errorBody()!!.string()}")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })
    }
    fun GetImageUrl(target: String, name: String, callback: RetrofitCallback<ResponseBody>) {
        RetrofitBuilder.api.GetImageUrl(target, name).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "GetImageUrl: ..")
                Log.d(TAG, "onResponse: ${response.code()}")
                Log.d(TAG, "onResponse: ${response.errorBody()}")
                Log.d(TAG, "onResponse: ${response.body()}")
                callback.onSuccess(response.code(), response.body()!!)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })
    }

    fun GetMedcineInfo(id: String, child_name:String, m_name:String, callback: RetrofitCallback<Medicine>) {
        RetrofitBuilder.api.GetMedicineInfo(id,child_name,m_name).enqueue(object : Callback<Medicine>{
            override fun onResponse(
                call: Call<Medicine>,
                response: Response<Medicine>
            ) {
                Log.d(TAG, "GetRoom: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<Medicine>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })
    }

    fun MedicineListShow(school: String, room: String, callback: RetrofitCallback<MutableList<MedicineManagementResult>>) {
        RetrofitBuilder.api.MedicineList(school, room).enqueue(object : Callback<MutableList<MedicineManagementResult>>{
            override fun onResponse(
                call: Call<MutableList<MedicineManagementResult>>,
                response: Response<MutableList<MedicineManagementResult>>
            ) {
                Log.d(TAG, "MedicineList: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<MedicineManagementResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })

    }
    fun ParentsMedicineListShow(id: String, child_name: String, callback: RetrofitCallback<MutableList<MedicineManagementResult>>) {
        RetrofitBuilder.api.parentsMedicineList(id, child_name).enqueue(object : Callback<MutableList<MedicineManagementResult>>{
            override fun onResponse(
                call: Call<MutableList<MedicineManagementResult>>,
                response: Response<MutableList<MedicineManagementResult>>
            ) {
                Log.d(TAG, "MedicineList: ..")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<MedicineManagementResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }

        })

    }

    fun CreateMedicine(info: PostMedicine, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.PostMedicine(info).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "CreateMedicineinfo: ..")
                Log.d(TAG, "onResponse: ${response.code()}")
                Log.d(TAG, "onResponse: ${response.errorBody()}")
                Log.d(TAG, "onResponse: ${response.message()}")
                Log.d(TAG, "onResponse: ${response.body()}")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }

    fun SchoolTeacherListShow(school: String, callback: RetrofitCallback<MutableList<SchoolteacherListResult>>) {
        RetrofitBuilder.api.SchoolteacherList(school).enqueue(object : Callback<MutableList<SchoolteacherListResult>>{
            override fun onResponse(
                call: Call<MutableList<SchoolteacherListResult>>,
                response: Response<MutableList<SchoolteacherListResult>>
            ) {
                Log.d(TAG, "SchoolteacherList: ..")
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: 200")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<SchoolteacherListResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                callback.onError(t)
            }
        })
    }



    fun CallAlarm(target_token: String, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.CallAlarm(target_token).enqueue(object : Callback<SignUpResult> {
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
            override fun onResponse(call: Call<SignUpResult>,response: Response<SignUpResult>) {
                Log.d(TAG, "CallAlarm: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
        })
    }
    fun GetAlbumInfo(school: String, room: String, callback: RetrofitCallback<MutableList<AlbumResult>>) {
        RetrofitBuilder.api.GetAlbumInfo(school, room).enqueue(object : Callback<MutableList<AlbumResult>>{
            override fun onResponse(call: Call<MutableList<AlbumResult>>, response: Response<MutableList<AlbumResult>>) {
                Log.d(TAG, "saveCalender: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<AlbumResult>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }

    fun Agreechange(agreechange: AgreeChange, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.agreechange(agreechange).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "saveCalender: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)            }

        })
    }

    fun Deletechildlist(agreechange: AgreeChange, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.deletechildlist(agreechange).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "saveCalender: ..")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun Teacheragreechange(id: TeacherListKeyId, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.Teacheragreechange(id).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "Teacheragreechange: ..")
                Log.d(TAG, "onResponse: ${response.code()}")

                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun deleteteacherlist(id: TeacherListKeyId, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.deleteteacherlist(id).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: teacher list delete")
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }

    fun CreateFoodList(foodlist: FoodList, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.CreateFoodList(foodlist).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "CreateFoodList: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun GetFoodListInfo(school: String, callback: RetrofitCallback<MutableList<GetFoodList>>){
        RetrofitBuilder.api.FoodListInfo(school).enqueue(object : Callback<MutableList<GetFoodList>>{
            override fun onResponse(call: Call<MutableList<GetFoodList>>, response: Response<MutableList<GetFoodList>>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }               }

            override fun onFailure(call: Call<MutableList<GetFoodList>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun GetFoodList(school: String, date: String, callback: RetrofitCallback<GetFoodList>){
        RetrofitBuilder.api.GetFoodList(school, date).enqueue(object : Callback<GetFoodList>{
            override fun onResponse(call: Call<GetFoodList>, response: Response<GetFoodList>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }              }

            override fun onFailure(call: Call<GetFoodList>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }
    fun GetCalenderInfo(school: String, date: String, callback: RetrofitCallback<MutableList<GetCalenderDetail>>){
        RetrofitBuilder.api.GetCalenderInfo(school, date).enqueue(object : Callback<MutableList<GetCalenderDetail>>{
            override fun onResponse(call: Call<MutableList<GetCalenderDetail>>, response: Response<MutableList<GetCalenderDetail>>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }              }

            override fun onFailure(call: Call<MutableList<GetCalenderDetail>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }
    fun InsertCalenderInfo(data : Calenderinfo, callback: RetrofitCallback<SignUpResult>) {
        RetrofitBuilder.api.insertCalender(data).enqueue(object : Callback<SignUpResult> {
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: 200")
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else {
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun SelectCalender(data : CalenderSelect, callback: RetrofitCallback<MutableList<CalenderResult>>){
        RetrofitBuilder.api.selectCalender(data).enqueue(object : Callback<MutableList<CalenderResult>>{
            override fun onResponse(call: Call<MutableList<CalenderResult>>, response: Response<MutableList<CalenderResult>>) {
                Log.d(TAG, "SelectCalender onResponse: ${response.body()!!}")
                if (response.code() == 200){
                    Log.d(TAG, "SelectCalender onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "SelectCalender onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }              }

            override fun onFailure(call: Call<MutableList<CalenderResult>>, t: Throwable) {
                Log.d(TAG, "SelectCalender onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun UpdateCalenderInfo(data : updateCalender, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.updateCalender(data).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }              }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun DeleteCalenderInfo(data : deleteCalender, callback: RetrofitCallback<SignUpResult>){
            RetrofitBuilder.api.deleteCalender(data).enqueue(object : Callback<SignUpResult>{
                override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                    Log.d(TAG, "onResponse: ${response.code()}")
                    if (response.code() == 200){
                        Log.d(TAG, "onResponse: 200")
                        if(response.body() != null){
                            Log.d(TAG, "onResponse: body is not null")
                            callback.onSuccess(response.code(), response.body()!!)
                        } else{
                            callback.onFailure(response.code())
                        }
                    }              }

                override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                    Log.d(TAG, "onFailure: ")
                    callback.onError(t)
                }
            })
    }

    fun UpdateParentinfo(ParentinfoUpdate : ParentInfoUpdate, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.UpdateParentinfo(ParentinfoUpdate).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun deleteNoticItem(agreeChange : AgreeChange, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.deleteNoticItem(agreeChange).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun UpdateNoticItem(updateNotic: UpdateNotic, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.UpdateNoticItem(updateNotic).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }

        })
    }

    fun DeleteChildItem(key_id: AgreeChange, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.DeleteChildItem(key_id).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }

    fun updateTeacherinfo(updateTeacherinfo: UpdateTeacherinfo, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.updateTeacherinfo(updateTeacherinfo).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }
    fun UpdatePW(id: ID, callback: RetrofitCallback<SignUpResult>){
        RetrofitBuilder.api.updateinfoPW(id).enqueue(object : Callback<SignUpResult>{
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: 200")
                    if(response.body() != null){
                        Log.d(TAG, "onResponse: body is not null")
                        callback.onSuccess(response.code(), response.body()!!)
                    } else{
                        callback.onFailure(response.code())
                    }
                }            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onError(t)
            }
        })
    }
}
