package com.example.mvvm_template.data.mapper

import com.example.mvvm_template.data.remote_service.response.ProfileResponse
import com.example.mvvm_template.data.remote_service.response.customer.toMemberType
import com.example.mvvm_template.data.remote_service.response.customer.toPoint
import com.example.mvvm_template.domain.entity.Profile

class MapProfileReponseToProfile:BaseMapper<ProfileResponse,Profile> {
    override fun map(t: ProfileResponse): Profile {
        return Profile(
            email = t.email,
            name = t.name,
            imageName= t.imageName,
            imagePath = t.imagePath,
            mobileNumber = t.mobileNumber,
            firstName=t.firstName,
            secondName= t.lastName,
            id =  t.id,
            sendCardCodeByEmail = t.sendCardCodeByEmail,
            sendCardCodeByMobileNumber = t.sendCardCodeByMobileNumber,
            memberType = t.memberType.toMemberType(),
            point = t.loyalityPoints?.toPoint()
        )
    }
}