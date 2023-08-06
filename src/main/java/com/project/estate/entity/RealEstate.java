package com.project.estate.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.estate.model.District;
import com.project.estate.model.Province;
import com.project.estate.model.Ward;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "real_estate")
@Entity
public class RealEstate extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private Integer type;

    @Column(name = "request")
    private Integer request;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "street_id")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JoinColumn(name = "address")
    private String address;

    @Column(name = "price")
    private Long price;

    @Column(name = "price_min")
    private Long priceMin;

    @Column(name = "acreage")
    private Long acreage; // Diện tích

    @Column(name = "direction")
    private Integer direction; // Hướng nhà

    @Column(name = "total_floors")
    private Integer totalFloors; // Tổng số tầng

    @Column(name = "number_floors")
    private Integer numberFloors; // Số tầng

    @Column(name = "bath")
    private Integer bath; // Số phòng tắm

    @Column(name = "apart_code")
    private String apartCode; // Mã căn hộ

    @Column(name = "wall_area")
    private Long wallArea; // Diện tích tường

    @Column(name = "bedroom")
    private Integer bedroom; // Số phòng ngủ

    @Column(name = "balcony")
    private Integer balcony; // Số ban công

    @Column(name = "landscape_view")
    private Integer landscapeView; // Loại cảnh quan

    @Column(name = "apart_location")
    private Integer apartLocation; // Vị trí căn hộ

    @Column(name = "apart_type")
    private Integer apartType; // Loại căn hộ

    @Column(name = "furniture_type")
    private Integer furnitureType; // Loại nội thất

    @Column(name = "price_rent")
    private Long priceRent; // Giá thuê

    @Column(name = "return_rate")
    private Integer returnRate; // Tỷ lệ sinh lời

    @Column(name = "legal_doc")
    private Integer legalDoc; // Giấy tờ pháp lý

    @Column(name = "description", length = 1000)
    private String description; // Mô tả (có độ dài tối đa 1000 ký tự)

    @Column(name = "width_y")
    private Long widthY; // Chiều rộng Y

    @Column(name = "long_x")
    private Long longX; // Chiều dài X

    @Column(name = "street_house")
    private Integer streetHouse; // Địa chỉ, tên đường

    @Column(name = "view_num")
    private Integer viewNum; // Số lượt xem

    @Column(name = "shape")
    private String shape; // Hình dạng (?)

    @Column(name = "distance2facade")
    private Long distance2facade; // Khoảng cách đến mặt tiền

    @Column(name = "adjacent_facade_num")
    private Integer adjacentFacadeNum; // Số mặt tiền kề

    @Column(name = "adjacent_road")
    private String adjacentRoad; // Mặt đường kề

    @Column(name = "alley_min_width")
    private Long alleyMinWidth; // Chiều rộng hẻm tối thiểu

    @Column(name = "adjacent_alley_min_width")
    private Long adjacentAlleyMinWidth; // Chiều rộng hẻm kề tối thiểu

    @Column(name = "factor")
    private Integer factor; // Faktor (?)

    @Column(name = "structure")
    private String structure; // Kết cấu (?)

    @Column(name = "DTSXD")
    private Long dtsxd; // Diện tích sàn xây dựng

    @Column(name = "CLCL")
    private Long clcl; // Chỉ số CLCL (?)

    @Column(name = "construction_cost")
    private Long constructionCost; // Chi phí xây dựng

    @Column(name = "construction_value")
    private Long constructionValue; // Giá trị CTXD (?)

    @Column(name = "photo")
    private Integer photo; // Số ảnh

    @Column(name = "lat")
    private Double lat; // Vĩ độ

    @Column(name = "lng")
    private Double lng; // Kinh độ

    @Column(name = "postedByOwner")
    private Boolean postedByOwner;

    @Column(name = "price_time")
    private Integer priceTime; // Thời gian giá

    @Column(name = "date_create")
    private Long dateCreate; // Ngày tạo

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;
}