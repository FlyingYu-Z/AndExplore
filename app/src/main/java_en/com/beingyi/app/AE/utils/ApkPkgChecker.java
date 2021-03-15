package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;import java.util.HashMap;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

public class ApkPkgChecker
{
    ZipFile zipFile;
    HashMap<String,String> map=new HashMap<String,String>();
    HashMap<String,String> proMap=new HashMap<String,String>();
    
    public ApkPkgChecker(String path){
        
        try
        {
            zipFile = new ZipFile(path);
        }
        catch (Exception e)
        {
            
        }

        map.put(BASE128.decode("yTkoiFDZaI1vEa24WWMuyA=="), BASE128.decode("gPYvUBorbTOVQjIo8L5bGQ=="));
        map.put(BASE128.decode("RpRP8fstnETKELG6BIw73A=="), BASE128.decode("gPYvUBorbTOVQjIo8L5bGQ=="));
        map.put(BASE128.decode("dpbSt77J2ZNODI4Nx4PZhw=="), BASE128.decode("gPYvUBorbTOVQjIo8L5bGQ=="));
        map.put(BASE128.decode("YcpHqz3hPfgSQLhGiWa52Q=="), BASE128.decode("R7l5DCUd+fEq4QvjFFD4xw=="));
        map.put(BASE128.decode("278RXbqzQ6EpQ3pBcrCIsA=="), BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        map.put(BASE128.decode("Jc8EPhIfdYL1Mziq55FjSw=="), BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        map.put(BASE128.decode("GYXvZwXqlNUKAQwJqdkCFg=="), BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        map.put(BASE128.decode("DMcIGAb4FO7nXOVb/svNkA=="), BASE128.decode("7a5i78paFeuMj0OD4mMGuUSLuasbeloIVcNXoQWmu0I="));
        map.put(BASE128.decode("uh81Y2QcI5I9TCUMTtNPyw=="), BASE128.decode("gW1PNi913+FAi0Qt3tL89A=="));
        map.put(BASE128.decode("2EdAfiHPvtxsu/b1Oe7UGA=="), BASE128.decode("gW1PNi913+FAi0Qt3tL89A=="));
        map.put(BASE128.decode("1EBBvYtKZtItnUc4uUk3PA=="), BASE128.decode("gW1PNi913+FAi0Qt3tL89A=="));
        map.put(BASE128.decode("ww6VznjR9NRiD5x356gs/A=="), BASE128.decode("0YQOEWIFWHmokG6TIJPUtw=="));
        map.put(BASE128.decode("pi20QBYdT3O1NbMmt6YyJT1G6Fmb/o4km7j7A0x7PDg="), BASE128.decode("0YQOEWIFWHmokG6TIJPUtw=="));
        map.put(BASE128.decode("h26a6xWD/GMfSj9ebh84sxEo7USvG7tLHg6H/b6skyE="), BASE128.decode("HhgckujEp7pOuAhBCGVyCA=="));
        map.put(BASE128.decode("aQ+JJNSVscLHtU31art0vg=="), BASE128.decode("HhgckujEp7pOuAhBCGVyCA=="));
        map.put(BASE128.decode("INQK66dY3Iu7isj50KbIug=="), BASE128.decode("HhgckujEp7pOuAhBCGVyCA=="));
        map.put(BASE128.decode("K+DimiIFPreYTBFzT2LvVw=="), BASE128.decode("HhgckujEp7pOuAhBCGVyCA=="));
        map.put(BASE128.decode("nAcErsYmA9jNecW2/AnxaA=="), BASE128.decode("IYwcbKOCouUYuWTqc9Ea9w=="));
        map.put(BASE128.decode("aoeeeQuW5YzEFi54ryUT6Bc6851N1Lg/CkAIgUvVwng="), BASE128.decode("IYwcbKOCouUYuWTqc9Ea9w=="));
        map.put(BASE128.decode("aS2+MY72xx/X+qEBFIarew=="), BASE128.decode("uytgqoDgQsYGHrFnnfjPsQ=="));
        map.put(BASE128.decode("Ckyyd30S6OoFC04pOYk1/hEo7USvG7tLHg6H/b6skyE="), BASE128.decode("1g0/zxFkoZmUs7i2oe238A=="));
        map.put(BASE128.decode("A1wK6ysTcHYLPjrH+SCtXw=="), BASE128.decode("FefuN1jq4IjSYt1tiuImMA=="));
        map.put(BASE128.decode("XqxyJAKjstlLG6MKOOD6TA=="), BASE128.decode("FefuN1jq4IjSYt1tiuImMA=="));
        map.put(BASE128.decode("oTRM0fOyM2k71C7v5iF/LXSO5fGM5DcKEOkeS0FMEhg="), BASE128.decode("FefuN1jq4IjSYt1tiuImMA=="));
        map.put(BASE128.decode("jII9cI5ieUeTXZs+zoiRsw=="), BASE128.decode("FefuN1jq4IjSYt1tiuImMA=="));
        map.put(BASE128.decode("mgnSbb36FpHoffQr1rGX0Q=="), BASE128.decode("px53O4pU1sN2Wl93WW6S0Q=="));
        map.put(BASE128.decode("278RXbqzQ6EpQ3pBcrCIsA=="), BASE128.decode("px53O4pU1sN2Wl93WW6S0Q=="));
        map.put(BASE128.decode("1Iv2LJzeGSkBK18380xigg=="), BASE128.decode("px53O4pU1sN2Wl93WW6S0Q=="));
        map.put(BASE128.decode("LXm6SdJOFvqhnToofdGBlw=="), BASE128.decode("px53O4pU1sN2Wl93WW6S0Q=="));
        map.put(BASE128.decode("IfhHcl1LUx6+pjgS94xY0w=="), BASE128.decode("px53O4pU1sN2Wl93WW6S0Q=="));
        map.put(BASE128.decode("EJFG11M6W7twP2RDryhbVBc6851N1Lg/CkAIgUvVwng="), BASE128.decode("px53O4pU1sN2Wl93WW6S0Q=="));
        map.put(BASE128.decode("6Y4pWuQCJ3/UA6YlembOcKYbBg5AEJW3Mbb2O9dTQEQ="), BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        map.put(BASE128.decode("6Y4pWuQCJ3/UA6YlembOcE8n/C8KXd4+6sDi3SMZAYY="), BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        map.put(BASE128.decode("6Y4pWuQCJ3/UA6YlembOcIO1k0xVsFmMKUFzgaJSdX8="), BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        map.put(BASE128.decode("pxw7nDWVmwUuQq6IQUmW2w=="), BASE128.decode("i4PyAxciGWRveHqmyQtNsg=="));
        map.put(BASE128.decode("MOjVWqYJiZRmvvE/J0Ik0Bc6851N1Lg/CkAIgUvVwng="), BASE128.decode("tcwjvUOWFgQC0lrRPYFOaw=="));
        map.put(BASE128.decode("3NF+D9ta/Gk/AJfhy2YeGA=="), BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        map.put(BASE128.decode("R03gBArndjayKYDilcE1mw=="), BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        map.put(BASE128.decode("TLdfKe6Yqy4vkPhrQ4D2ZA=="), BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        map.put(BASE128.decode("Q1TEBRd80yt5qywjeWIkSw=="), BASE128.decode("QD0JMziaqW4mgMSSX3JVxQ=="));
        map.put(BASE128.decode("LReoZQKiniSlgOf8tX5beg=="), BASE128.decode("Gw7KtajQshH13omGAffa+w=="));
        map.put(BASE128.decode("tc3ZaI5z2rK2O4/zSwkLGg=="), BASE128.decode("ZgUn97937oqAeGq/Vru2WA=="));
        map.put(BASE128.decode("YcpHqz3hPfgSQLhGiWa52Q=="),BASE128.decode("gPYvUBorbTOVQjIo8L5bGQ=="));
        map.put(BASE128.decode("nAcErsYmA9jNecW2/AnxaA=="),BASE128.decode("IYwcbKOCouUYuWTqc9Ea9w=="));
        map.put(BASE128.decode("LReoZQKiniSlgOf8tX5beg=="),BASE128.decode("pR3h8m6rxZc6XtrCqy5xLg=="));
        map.put(BASE128.decode("tc3ZaI5z2rK2O4/zSwkLGg=="),BASE128.decode("M9hSpa2X8cM5wok57jOWLQ=="));
        map.put(BASE128.decode("aS2+MY72xx/X+qEBFIarew=="),BASE128.decode("70ad7albEuN94tiGAkl/vA=="));
        map.put(BASE128.decode("ajEAOhvjjUHsPAaM80dORg=="),BASE128.decode("LyJXiyT+D5g2EankZL6Low=="));
        map.put(BASE128.decode("0VlqkJmzlB2OUouLIxUwfw=="),BASE128.decode("LyJXiyT+D5g2EankZL6Low=="));



        proMap.put(BASE128.decode("hkZtWtYjiALHPLc0LdIT/w=="),BASE128.decode("HhgckujEp7pOuAhBCGVyCA=="));
        proMap.put(BASE128.decode("DI9QKe34LSNCtBECM+U7phEo7USvG7tLHg6H/b6skyE="),BASE128.decode("HhgckujEp7pOuAhBCGVyCA=="));
        proMap.put(BASE128.decode("tDz6dQRBaw8zJlWzCj50V1qkhmj4HWlLMrCq8FuvDVo="),BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        proMap.put(BASE128.decode("HC+L9pyIH3gkgMeu12ny5GyNPM4fZqTQQ1GkmrT+Y5E="),BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        proMap.put(BASE128.decode("dUDp531qLprXhsOChSG5Uw=="),BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        proMap.put(BASE128.decode("M+T8+Gecd1ZqoOA6brMWZ78TwA5d6sLUGBpNXVu7JvU="),BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        proMap.put(BASE128.decode("90aPa9CrGpvuiSOTlDV9A3JOnlsmWQwPibACLqUCSAaflpihIE3OJGdyLzLWEo2H"),BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        proMap.put(BASE128.decode("JYZVqOoLe5WIHdbgRqSXMNTcfLTQcuUbzoEPu2xQPl8="),BASE128.decode("/u2cq8du4fk1jXM61k6dLw=="));
        proMap.put(BASE128.decode("ggk+QDqcQLPeUMiN90lI/A=="),BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        proMap.put(BASE128.decode("OI5edqYFgVGbZYsKBW+99o2q/r9eyM05S9ef64ps+QY="),BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        proMap.put(BASE128.decode("KEqL+YNblWWrb7WLV9fm7li9VswZYFijK9JPqSzSS38="),BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        proMap.put(BASE128.decode("KEqL+YNblWWrb7WLV9fm7pSArqrYWEui7KqLPhhfQ9I="),BASE128.decode("El9zKDWEPxkBtkiRbS5w6g=="));
        proMap.put(BASE128.decode("sNKIdxMQ0Mi0l+vBZqgJNu+6v6S2KgiI+EMrJGdukgo="),BASE128.decode("XE/xNXd8CCkoJJO14xsANWQ2aWtU7EM4Q22Yvi1azlQ="));
        proMap.put(BASE128.decode("sNKIdxMQ0Mi0l+vBZqgJNu+6v6S2KgiI+EMrJGdukgo="),BASE128.decode("XE/xNXd8CCkoJJO14xsANWQ2aWtU7EM4Q22Yvi1azlQ="));
        proMap.put(BASE128.decode("sNKIdxMQ0Mi0l+vBZqgJNu+6v6S2KgiI+EMrJGdukgo="),BASE128.decode("XE/xNXd8CCkoJJO14xsANWQ2aWtU7EM4Q22Yvi1azlQ="));
        proMap.put(BASE128.decode("lHloRj0ISJPLqnrOCXi01iCMZecUcoC1870WYBcNzY4="),BASE128.decode("rVVWNeFbEc+iNhoO8vn16MCYEUGwhiR5p9BsXhVhiGU="));
        proMap.put(BASE128.decode("pAN+J6OkAYltIW4T+IYtQjC0KyrkOzRUepyaRswOUz0="),BASE128.decode("rVVWNeFbEc+iNhoO8vn16MCYEUGwhiR5p9BsXhVhiGU="));
        proMap.put(BASE128.decode("VUPGH2aOqtpA44Gnv8C4LyrycnDBqIc+FwX7g89PqbaJ9WA+zAAVQ3+FqEJA4wvm"),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("VUPGH2aOqtpA44Gnv8C4LyrycnDBqIc+FwX7g89PqbaJ9WA+zAAVQ3+FqEJA4wvm"),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("VUPGH2aOqtpA44Gnv8C4L6kreIqcMfz6D7OnsX4YJsQRKO1Erxu7Sx4Oh/2+rJMh"),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("VUPGH2aOqtpA44Gnv8C4L43dNYib8YsLXPlfccteKI0="),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("SoxW6S1f1hMMmU4w7Clehb8TwA5d6sLUGBpNXVu7JvU="),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("COCvUF9EEahrnsfug1SjUpyxvStcfLPvyHcmgl6Naa8="),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("InP4MO9SV9y+FMXm/0SQUTlbAvNKig55AWnfEArpgmM="),BASE128.decode("mk4LxHSbnJ43hUSZGgAG2Q=="));
        proMap.put(BASE128.decode("ps2nveH333cPwLozWKIQ5Q=="),BASE128.decode("JslGkL8Bej70l/z0TMo280nJKNNhLWgl1hwOA9/Hfmo="));
        proMap.put(BASE128.decode("7aAucqgEdMz1Byx7m5bnwKDRLMa5Mved68Oskj+Rp8M="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("7aAucqgEdMz1Byx7m5bnwJa45Skk5tZ3y1dVcfEJxGE="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("AlZj/pihs2uMd7GPipB8wJ+WmKEgTc4kZ3IvMtYSjYc="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("pwPWLye1nxakAhOxMsZyQ+LbOzy7dbkWAwUjTUaaWW4="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("l2yjeiBQ3bSYfH5Xq98FKdyP2TZqr0W+8GXvjjmsfY4="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("0Ql/e0H/cwdw19/DdTm4DA=="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("t6Oiz2yoEXnFUlTVZPEAVDCM+PcvPBmZE5fPG3bjahA="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("QC2BcO54csjojxuESdIJHFW4ofnlWCUNNrEcYAIR2+c="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("5OQHTcDXOV86wmipp7WcZxLyRGjOr4yYB5S6CtpD5s4="),BASE128.decode("iI+DNIyowVPuhKx8ZdggvA=="));
        proMap.put(BASE128.decode("z8Z35/Rccvlryxaeh7yEojeGuCbNtPzs8fZEmDh+M2g="),BASE128.decode("8ymFQypo4+Bx3dRNcbXWzg=="));
        proMap.put(BASE128.decode("T4tKU6kaInNfbG1ussn7Kr6x8MHy5G9zLZ4PqBaXvbw="),BASE128.decode("8ymFQypo4+Bx3dRNcbXWzg=="));
        proMap.put(BASE128.decode("T4tKU6kaInNfbG1ussn7KtmblQDg3xwE38+wI48O+/o="),BASE128.decode("8ymFQypo4+Bx3dRNcbXWzg=="));
        proMap.put(BASE128.decode("hXslpF+vrJa5tpBBcz0s/Q=="),BASE128.decode("C9pGaBedvT01WuGCmapf/w=="));
        proMap.put(BASE128.decode("eQFwy4//jeIiDXBB3mf56uO7DX3FvXGI3szzIlgIn74="),BASE128.decode("C9pGaBedvT01WuGCmapf/w=="));
        proMap.put(BASE128.decode("k909CjJagsE4qTv+/r4YlQ=="),BASE128.decode("C9pGaBedvT01WuGCmapf/w=="));
        proMap.put(BASE128.decode("ob0Qo22wLvFAbvtNSNyWzCAneZTkjlPDgKv9HLmevwk="),BASE128.decode("C9pGaBedvT01WuGCmapf/w=="));
        proMap.put(BASE128.decode("MeOZPatNzyy2qsdblbcVtw=="),BASE128.decode("C9pGaBedvT01WuGCmapf/w=="));
        proMap.put(BASE128.decode("/NMUI3VtGVf7Zjl9W3CUYzCYlSvPoUNHs+3q/OFLR2o9RuhZm/6OJJu4+wNMezw4"),BASE128.decode("C9pGaBedvT01WuGCmapf/w=="));
        
        
        

        proMap.put(BASE128.decode("L2FTu0YqL6wDThuZ3Rv0HWqW1SvDgRW5hW38bqPWFwY="),BASE128.decode("CP9N/EpWmIHi5KWl22DONg=="));
        proMap.put(BASE128.decode("L2FTu0YqL6wDThuZ3Rv0HT1G6Fmb/o4km7j7A0x7PDg="),BASE128.decode("CP9N/EpWmIHi5KWl22DONg=="));
        proMap.put(BASE128.decode("NFrPibOsMBIGSWwiHussegJ1ajRtgxIWXRgOgRM9sso="),BASE128.decode("CP9N/EpWmIHi5KWl22DONg=="));
        proMap.put(BASE128.decode("5gy5LiMQ6wfy1aJvUbHIcc+rRL+365ouciq6XsCnVA4="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("49/8+lWClRR3l7I+Ji5H0wafb29BX1q3rAva4Nz5ggw="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("49/8+lWClRR3l7I+Ji5H07eyt9Sz7woi/y92bxCJRRw="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("Hw5pmMTfs3L5MC7EKhV5DxNLNUyp83B+fXEZ48J7qmU="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("AShMJxzTI1ggs2vUx/LR8I6SU8A5Qo6EWcGWNHGB0NQ="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("AstAtI5PQSgvZKI8Mrzfrlfe50Z3xB4tBs+G9KErK0Q="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("AstAtI5PQSgvZKI8MrzfrgAz3g3ubxmotJOrpwatpHE9RuhZm/6OJJu4+wNMezw4"),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("AstAtI5PQSgvZKI8MrzfrjOHoQQ8ybE9CKJqEof4oo8="),BASE128.decode("hlieKy6RPF9sgbyuTNDBY9Ci+2EN1/c7AJpwQG0ti1A="));
        proMap.put(BASE128.decode("6gg/JkrFvqzCn5cnhFAymz1G6Fmb/o4km7j7A0x7PDg="),BASE128.decode("/HlTo1SaxXcKQqtbs7LCkg=="));
        proMap.put(BASE128.decode("6gg/JkrFvqzCn5cnhFAym3mK3QgLP7gGVwAEVgQ2EKc="),BASE128.decode("/HlTo1SaxXcKQqtbs7LCkg=="));
        
        
        
    }
    
    
    
    
    public String getPkgName(){
        
        for(String key :map.keySet()){
            ZipEntry entry=zipFile.getEntry(BASE128.decode("R39/QTKUaEtteCiLtFUhVg==")+key);
            if(entry!=null){
                return map.get(key);
            }
            
            ZipEntry entry2=zipFile.getEntry(BASE128.decode("P1ZSysYbHRxr+IGpJeI8nQ==")+key);
            if(entry2!=null){
                return map.get(key);
            }
        }
        
        for(String key :proMap.keySet()){
            ZipEntry entry=zipFile.getEntry(key);
            if(entry!=null){
                return proMap.get(key);
            }
            
        }
        
        
        return BASE128.decode("zeHWQNyQaWkjvlFkDXuJGA==");
    }
    
}
