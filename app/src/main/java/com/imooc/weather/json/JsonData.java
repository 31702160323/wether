package com.imooc.weather.json;

public class JsonData {

    /**
     * code : 1
     * msg : 数据返回成功，现已提供app_id方式请求接口，不限速，不限流，不封IP，可在自建服务器调用api，欢迎升级使用，详情请访问：https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F
     * data : {"address":"广东省 深圳市","cityCode":"440300","temp":"15℃","weather":"阴","windDirection":"北","windPower":"≤3级","humidity":"40%","reportTime":"2019-12-05 13:13:45"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 广东省 深圳市
         * cityCode : 440300
         * temp : 15℃
         * weather : 阴
         * windDirection : 北
         * windPower : ≤3级
         * humidity : 40%
         * reportTime : 2019-12-05 13:13:45
         */

        private String address;
        private String cityCode;
        private String temp;
        private String weather;
        private String windDirection;
        private String windPower;
        private String humidity;
        private String reportTime;

        private String[] ids = {"城市", "天气描述", "风向描述", "风力描述", "湿度值", "发布时间"};
        private String[] datas = new String[6];

        public String[] getIds() {
            return ids;
        }

        public String[] getDatas() {
            datas[0] = address;
            datas[1] = weather;
            datas[2] = windDirection;
            datas[3] = windPower;
            datas[4] = humidity;
            datas[5] = reportTime;
            return datas;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(String windDirection) {
            this.windDirection = windDirection;
        }

        public String getWindPower() {
            return windPower;
        }

        public void setWindPower(String windPower) {
            this.windPower = windPower;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }
    }
}
