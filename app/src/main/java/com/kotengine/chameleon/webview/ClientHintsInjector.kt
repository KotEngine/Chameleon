package com.kotengine.chameleon.webview

import com.kotengine.chameleon.data.DeviceProfile

object ClientHintsInjector {

    fun buildScript(profile: DeviceProfile): String {
        val brandsJson = """
            [
                {"brand":"Not/A)Brand","version":"8"},
                {"brand":"Chromium","version":"${majorVersion(profile.chUaFullVersion)}"},
                {"brand":"Google Chrome","version":"${majorVersion(profile.chUaFullVersion)}"}
            ]
        """.trimIndent()

        return """
        (function() {
            try {
                const UA = ${jsString(profile.userAgent)};
                const PLATFORM = "Linux ${profile.chModel}";
                const BRAND = ${jsString(profile.chBrand)};
                const MODEL = ${jsString(profile.chModel)};
                const PLATFORM_VERSION = ${jsString(profile.chPlatformVersion)};
                const UA_FULL_VERSION = ${jsString(profile.chUaFullVersion)};
                const ARCHITECTURE = ${jsString(profile.chArchitecture)};
                const BITNESS = ${jsString(profile.chBitness)};
                const MOBILE = ${profile.chMobile};
                const BRANDS = $brandsJson;

                Object.defineProperty(navigator, 'userAgent', { get: () => UA, configurable: true });
                Object.defineProperty(navigator, 'appVersion', { get: () => UA.replace('Mozilla/', ''), configurable: true });
                Object.defineProperty(navigator, 'platform', { get: () => 'Linux armv8l', configurable: true });
                Object.defineProperty(navigator, 'vendor', { get: () => 'Google Inc.', configurable: true });

                const highEntropyValues = {
                    architecture: ARCHITECTURE,
                    bitness: BITNESS,
                    brands: BRANDS,
                    mobile: MOBILE,
                    model: MODEL,
                    platform: 'Android',
                    platformVersion: PLATFORM_VERSION,
                    uaFullVersion: UA_FULL_VERSION,
                    fullVersionList: BRANDS.map(b => ({ brand: b.brand, version: UA_FULL_VERSION })),
                    wow64: false
                };

                const uaData = {
                    brands: BRANDS,
                    mobile: MOBILE,
                    platform: 'Android',
                    getHighEntropyValues: function(hints) {
                        const result = {};
                        (hints || []).forEach(h => {
                            if (h in highEntropyValues) result[h] = highEntropyValues[h];
                        });
                        result.brands = BRANDS;
                        result.mobile = MOBILE;
                        result.platform = 'Android';
                        return Promise.resolve(result);
                    },
                    toJSON: function() {
                        return { brands: BRANDS, mobile: MOBILE, platform: 'Android' };
                    }
                };

                Object.defineProperty(navigator, 'userAgentData', { get: () => uaData, configurable: true });
            } catch (e) {
                console.warn('Chameleon spoof injection failed', e);
            }
        })();
        """.trimIndent()
    }

    private fun jsString(value: String): String =
        "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""

    private fun majorVersion(fullVersion: String): String =
        fullVersion.substringBefore('.').ifBlank { "128" }
}
