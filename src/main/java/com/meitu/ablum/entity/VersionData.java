package com.meitu.ablum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 版本号
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionData {
    private Integer id;
    private String version;
}
