package com.kotengine.chameleon.data

data class DeviceProfile(
    val id: String,
    val brand: String,
    val marketingName: String,
    val userAgent: String,
    val chBrand: String,
    val chModel: String,
    val chPlatform: String = "Android",
    val chPlatformVersion: String,
    val chUaFullVersion: String,
    val chArchitecture: String = "",
    val chBitness: String = "64",
    val chMobile: Boolean = true,
)

val ORIGINAL_DEVICE = DeviceProfile(
    id = "original",
    brand = "Original",
    marketingName = "Без подмены",
    userAgent = "",
    chBrand = "",
    chModel = "",
    chPlatformVersion = "",
    chUaFullVersion = "",
)

val DEVICE_PROFILES: List<DeviceProfile> = listOf(
    DeviceProfile(
        id = "poco_x6_pro",
        brand = "POCO",
        marketingName = "POCO X6 Pro",
        userAgent = "Mozilla/5.0 (Linux; Android 14; 23122PCD1G) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36",
        chBrand = "POCO",
        chModel = "23122PCD1G",
        chPlatformVersion = "14.0.0",
        chUaFullVersion = "128.0.6613.127",
    ),
    DeviceProfile(
        id = "poco_f6",
        brand = "POCO",
        marketingName = "POCO F6",
        userAgent = "Mozilla/5.0 (Linux; Android 14; 24069PC21G) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36",
        chBrand = "POCO",
        chModel = "24069PC21G",
        chPlatformVersion = "14.0.0",
        chUaFullVersion = "128.0.6613.127",
    ),
    DeviceProfile(
        id = "poco_f6_pro",
        brand = "POCO",
        marketingName = "POCO F6 Pro",
        userAgent = "Mozilla/5.0 (Linux; Android 14; 24122RN91Y) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36",
        chBrand = "POCO",
        chModel = "24122RN91Y",
        chPlatformVersion = "14.0.0",
        chUaFullVersion = "128.0.6613.127",
    ),
    DeviceProfile(
        id = "redmi_note_13_pro_plus",
        brand = "Redmi",
        marketingName = "Redmi Note 13 Pro+",
        userAgent = "Mozilla/5.0 (Linux; Android 13; 23090RA98C) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36",
        chBrand = "Redmi",
        chModel = "23090RA98C",
        chPlatformVersion = "13.0.0",
        chUaFullVersion = "128.0.6613.127",
    ),
    DeviceProfile(
        id = "samsung_s24",
        brand = "Samsung",
        marketingName = "Galaxy S24",
        userAgent = "Mozilla/5.0 (Linux; Android 14; SM-S921B) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36",
        chBrand = "Samsung",
        chModel = "SM-S921B",
        chPlatformVersion = "14.0.0",
        chUaFullVersion = "128.0.6613.127",
    ),
)
