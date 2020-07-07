package com.shareeverything.service;

import com.shareeverything.Util.ShareEverythingUtils;
import com.shareeverything.constant.BookingStatus;
import com.shareeverything.constant.ChargingScheduleEnum;
import com.shareeverything.constant.ProductStatus;
import com.shareeverything.constant.ResponseStatus;
import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.request.AmountToPayRequestDto;
import com.shareeverything.dto.request.BookingSetRequestDto;
import com.shareeverything.dto.request.PostRequestDto;
import com.shareeverything.dto.response.AmountToPayResponseDto;
import com.shareeverything.dto.response.PostResponseDto;
import com.shareeverything.entity.*;
import com.shareeverything.exception.ShareEverythingException;
import com.shareeverything.repository.*;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    ChargingScheduleRepository chargingScheduleRepository;

    @Autowired
    BookingInfoRepository bookingInfoRepository;

    @Override
    public BaseResponseDto addPost(PostRequestDto postRequestDto,String userId) {

        User user = userRepository.getOne(UUID.fromString(userId));

        if(user == null){
            throw new ShareEverythingException("Post added failed. User not found.");
        }

        Post post = mapperFactory.getMapperFacade().map(postRequestDto,Post.class);

        post.setUser(user);

        Zone zone = zoneRepository.getOne(UUID.fromString(postRequestDto.getZoneId()));
        post.setZone(zone);

        City city = cityRepository.getOne(UUID.fromString(postRequestDto.getCityId()));
        post.setCity(city);

        Category category = categoryRepository.getOne(UUID.fromString(postRequestDto.getCategoryId()));
        post.setCategory(category);

        if(postRequestDto.getSubCategoryId() != null && !StringUtils.isEmpty(postRequestDto.getSubCategoryId())){
            SubCategory subCategory = subCategoryRepository.getOne(UUID.fromString(postRequestDto.getSubCategoryId()));
            post.setSubCategory(subCategory);
            log.info("Sub-category added. Sub-category:{}",post.getSubCategory().getName());
        }

        ChargingSchedule chargingSchedule = chargingScheduleRepository.getOne(UUID.fromString(postRequestDto.getChargingScheduleId()));
        post.setChargingSchedule(chargingSchedule);

        post.setCreatedAt(new Date());

        post.setProductStatus(ProductStatus.AVAILABLE);

        postRepository.save(post);

        log.info("Post added. title:{} description:{} location:{} contact-no:{} zone:{} city:{} category:{} charging-schedule:{} start-date:{} expired-date:{} free:{}",
                post.getTitle(),post.getDescription(),post.getLocation(),post.getContactNo(),post.getZone().getName(),post.getCity().getName()
                ,post.getCategory().getName(),post.getChargingSchedule().getName(),post.getStartDate(),post.getExpireDate(),post.getIsFree()
        );

        BaseResponseDto baseResponseDto = BaseResponseDto.builder()
                .message("Post added successfully")
                .status(ResponseStatus.SUCCESS)
                .build();

        return baseResponseDto;
    }

    @Override
    public List<PostResponseDto> getAllPost(String userId) {

        User user = userRepository.getOne(UUID.fromString(userId));
        if(user == null){
            log.error("User not found. user-id: {}",userId);
            throw new ShareEverythingException("User not found.");
        }

        List<Post> posts = postRepository.findAllByUser(user);
        List<PostResponseDto> postResponseDtos = mapperFactory.getMapperFacade().mapAsList(posts,PostResponseDto.class);
        return postResponseDtos;
    }

    @Override
    public BaseResponseDto updatePost(PostRequestDto postRequestDto,String postId) {

        Post post = postRepository.getOne(UUID.fromString(postId));

        if(post == null){
            log.error("Post not found. post-id: {}",postId);
            throw new ShareEverythingException("Post not found.");
        }
        if(!StringUtils.isEmpty(postRequestDto.getTitle())){
            post.setTitle(postRequestDto.getTitle());
        }

        if(!StringUtils.isEmpty(postRequestDto.getDescription())){
            post.setDescription(postRequestDto.getDescription());
        }

        if(!StringUtils.isEmpty(postRequestDto.getLocation())){
            post.setLocation(postRequestDto.getLocation());
        }

        if(!StringUtils.isEmpty(postRequestDto.getContactNo())){
            post.setContactNo(postRequestDto.getContactNo());
        }

        if(!StringUtils.isEmpty(postRequestDto.getAmout())){
            post.setAmount(postRequestDto.getAmout());
        }

        if(!StringUtils.isEmpty(postRequestDto.getIsfree())){
            post.setIsFree(postRequestDto.getIsfree());
        }

        if(!StringUtils.isEmpty(postRequestDto.getChargingScheduleId())){
            ChargingSchedule chargingSchedule = chargingScheduleRepository.getOne(UUID.fromString(postRequestDto.getChargingScheduleId()));
            post.setChargingSchedule(chargingSchedule);
        }

        if(!StringUtils.isEmpty(postRequestDto.getExpireDate())){
            post.setExpireDate(ShareEverythingUtils.getStringAsDate(postRequestDto.getExpireDate()));
        }

        post.setUpdatedAt(new Date());
        postRepository.save(post);
        log.info("Post updated. post-id: {}",postId);
        BaseResponseDto baseResponseDto = BaseResponseDto.builder()
                .status(ResponseStatus.SUCCESS)
                .message("Post updated successfully.")
                .build();
        return baseResponseDto;
    }

    @Override
    public PostResponseDto getPost(String postId, String userId) {
        User user = User.builder().build();
        user.setId(UUID.fromString(userId));
        Post post = postRepository.findByIdAndUser(postId,user);
        if(post == null){
            log.error("Post not found. post-id: {} user-id: {}",postId,userId);
            throw new ShareEverythingException("Post not found.");
        }

        return mapperFactory.getMapperFacade().map(post, PostResponseDto.class);
    }


    @Override
    public List<PostResponseDto> getPostFeed() {
        List<Post> posts = postRepository.findByStartDateLessThanEqualAndProductStatus(new Date(),ProductStatus.AVAILABLE);
        List<PostResponseDto> postResponseDtos = mapperFactory.getMapperFacade().mapAsList(posts,PostResponseDto.class);
        return postResponseDtos;
    }

    @Override
    public PostResponseDto getPostDetails(String postId) {
        Post post = postRepository.findById(postId);
        if(post == null){
            log.error("Post not found. post-id: {}",postId);
            throw new ShareEverythingException("Post not found.");
        }

        return mapperFactory.getMapperFacade().map(post, PostResponseDto.class);
    }

    @Override
    public AmountToPayResponseDto getAmountToPay(AmountToPayRequestDto amountToPayRequestDto, String postId) {
        Post post = postRepository.findByIdAndProductStatus(postId,ProductStatus.AVAILABLE);
        if(post == null){
            log.error("Post not found. post-id: {}",postId);
            throw new ShareEverythingException("Post not found.");
        }
        if((ShareEverythingUtils.getStringAsDate(amountToPayRequestDto.getStartDate())
                .compareTo(post.getStartDate()) <0) ||
                (ShareEverythingUtils.getStringAsDate(amountToPayRequestDto.getEndDate())
                        .compareTo(post.getExpireDate()) >0)){

            log.error("Invalid Start Date/End Date. start-date: {}, end-date: {}, post-start-date: {}, post-end-date: {}",amountToPayRequestDto.getStartDate(),
                    amountToPayRequestDto.getEndDate(),ShareEverythingUtils.getDateAsString(post.getStartDate()),ShareEverythingUtils.getDateAsString(post.getExpireDate()));
            throw new ShareEverythingException("Invalid Start Date/End Date");

        }
        return AmountToPayResponseDto.builder()
                .amountToPay(amountToPay(post,amountToPayRequestDto.getStartDate(),amountToPayRequestDto.getEndDate()))
                .endDate(amountToPayRequestDto.getEndDate())
                .startDate(amountToPayRequestDto.getStartDate())
                .build();
    }

    @Override
    public BaseResponseDto setBooking(String userId, BookingSetRequestDto bookingSetRequestDto) {

        User user = userRepository.getOne(UUID.fromString(userId));
        if(user == null){
            log.error("User not found. user-id: {}",userId);
            throw new ShareEverythingException("User not found.");
        }
        Post post = postRepository.findByIdAndProductStatus(bookingSetRequestDto.getPostId(),ProductStatus.AVAILABLE);
        if(post == null){
            log.error("Post not found. post-id: {}",bookingSetRequestDto.getPostId());
            throw new ShareEverythingException("Post not found.");
        }
        BookingInformation bookingInformation = BookingInformation.builder()
                .bookingStatus(BookingStatus.PENDING)
                .amoutToPay(bookingSetRequestDto.getAmountToPay())
                .post(post)
                .user(user)
                .startDate(ShareEverythingUtils.getStringAsDate(bookingSetRequestDto.getStartDate()))
                .endDate(ShareEverythingUtils.getStringAsDate(bookingSetRequestDto.getEndDate()))
                .createdAt(new Date())
                .build();
        bookingInfoRepository.save(bookingInformation);


        return BaseResponseDto.builder()
                .status(ResponseStatus.SUCCESS)
                .message("Booking hase been set.")
                .build();
    }

    private Double amountToPay(Post post,String startDate,String endDate){

        Double amountToPay = 0.0;
        if(post.getChargingSchedule().getName().equals(ChargingScheduleEnum.DAILY)){
            Integer numberOfDays = ShareEverythingUtils.getNumberOfDays(startDate,endDate);
            amountToPay = post.getAmount()*numberOfDays;
        } else if(post.getChargingSchedule().getName().equals(ChargingScheduleEnum.WEEKLY)){
            Integer numberOfDays = ShareEverythingUtils.getNumberOfWeeks(ShareEverythingUtils.getStringAsDate(startDate),ShareEverythingUtils.getStringAsDate(endDate));
            amountToPay = post.getAmount()*numberOfDays;
        } else if(post.getChargingSchedule().getName().equals(ChargingScheduleEnum.MONTHLY)){
            Integer numberOfDays = ShareEverythingUtils.getNumberOfMonth(startDate,endDate);
            amountToPay = post.getAmount()*numberOfDays;
        }

        return amountToPay;

    }
}
