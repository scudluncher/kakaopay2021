package kakaopay.membership.controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import kakaopay.membership.common.CustomResponse;
import kakaopay.membership.domain.UserMembershipInfo;
import kakaopay.membership.domain.dto.UserMembershipInfoDTO;
import kakaopay.membership.domain.dto.UserMembershipReqDTO;
import kakaopay.membership.service.UserMembershipInfoService;

@RestController
@Validated
public class MembershipRestController {
    
    @Autowired
    private UserMembershipInfoService userMembershipInfoService;


    @GetMapping("")
    public String mainPage(){
        return "hello world";
    }

    final private String userIdCheck = "X-USER-ID is blank";

    //요구사항1
    @GetMapping("/api/v1/membership")
    public ResponseEntity<CustomResponse> getMembershipInfoByUserId(@RequestHeader(value="X-USER-ID")@Size(min=1, message= userIdCheck) String userId){
        List <UserMembershipInfoDTO> dtoList = userMembershipInfoService.getMembershipInfoByUserIdAsDTO(userId);
        CustomResponse rep = new CustomResponse(dtoList);
        return ResponseEntity.ok(rep);
    }


    
    @PostMapping(value = "/api/v1/membership", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveMembershipInfoByUserId(@RequestHeader(value="X-USER-ID")@Size(min=1, message= userIdCheck) String userId, 
                                                                     @RequestBody @Valid UserMembershipReqDTO reqDto){
        
        UserMembershipInfo membershiInfo = userMembershipInfoService.createMembershipInfoByUserId(userId, reqDto);
        List <UserMembershipInfoDTO> dtoList = userMembershipInfoService.getMembershipInfoByUserIdAsDTO(membershiInfo.getUser().getUserId());
        CustomResponse rep = new CustomResponse(dtoList);
        return ResponseEntity.ok(rep);
    }


    @DeleteMapping("/api/v1/membership/{membershipId}")
    public ResponseEntity<CustomResponse> deactivateMembershipInfoByUserId(@RequestHeader(value="X-USER-ID")@Size(min=1, message= userIdCheck) String userId, 
                                                                           @PathVariable String membershipId){
        userMembershipInfoService.deactivateMembershipInfoByUserId(userId, membershipId);
        CustomResponse rep = new CustomResponse(true);
        return ResponseEntity.ok(rep);
    }

    @GetMapping(value = "/api/v1/membership/{membershipId}")
    public ResponseEntity<CustomResponse> getMembershipByMembershipIdAndUserId(@RequestHeader(value="X-USER-ID")@Size(min=1, message= userIdCheck) String userId,
                                                                               @PathVariable String membershipId){
        UserMembershipInfoDTO infoDto = userMembershipInfoService.getMembershipByMembershipIdAndUserId(userId, membershipId);
        CustomResponse rep = new CustomResponse(infoDto);
        return ResponseEntity.ok(rep);
    }

    @PutMapping(value = "/api/v1/membership/point", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> addMembershipPointDependingAmountForUser(@RequestHeader(value="X-USER-ID")@Size(min=1, message= userIdCheck) String userId,
                                                                                   @RequestBody @Valid UserMembershipReqDTO reqDto){
        userMembershipInfoService.addMembershipPointDependingAmountForUser(userId, reqDto);
        CustomResponse rep = new CustomResponse(true);
        return ResponseEntity.ok(rep);
    }


}