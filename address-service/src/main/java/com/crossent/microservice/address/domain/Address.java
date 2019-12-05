package com.crossent.microservice.address.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "aId", nullable = false, unique = true)
	private long aId;

	@NonNull
	@Column
	private String sido;
    @NonNull
	@Column
	private String gugun;
    @NonNull
	@Column
	private String dong;
    @NonNull
	@Column
    private String code;

    public Address(String sido, String gugun, String dong, String code){
    	this.sido = sido;
    	this.gugun = gugun;
    	this.dong = dong;
    	this.code = code;
	}

}
