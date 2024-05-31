public class Website {
    private final String pageTitle;
    private final String urlAddress;
    private final String rssUrl;

    public Website(String pageTitle, String urlAddress, String rssUrl) {
        this.pageTitle = pageTitle;
        this.urlAddress = urlAddress;
        this.rssUrl = rssUrl;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public String getRssUrl() {
        return rssUrl;
    }

}
