# TouristAttraction
Tourist Attraction

已經發布公測版本方便快速下載查看應用：  
<a href='https://play.google.com/store/apps/details?id=com.mason.touristattractionshw&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img width='300' alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a>

根據 https://www.travel.taipei/open-api/zh-tw/Attractions/All 實作  
取得選取語系全部景點，透過recyclerView顯示在主頁面上。  
並且在點擊每個景點後，打開新的頁面顯示景點詳細資訊，同時可以直接透過url開啟webview瀏覽器顯示官方網頁。


*實作影片連結：https://youtu.be/_DhneUvSYYE*


實作時間較長，後續還有針對recyclerView調整使用Paging3的緩存機制，  
因此只有錄製前面刻畫UI、主頁面Fragments、景點頁面Fragment、Retrofit讀取資訊。


* 架構:   MVVM 
    * AttractionViewModel: 主頁面取得Flow，顯示在RecyclerView上，更新語系
    * AttractionDetailViewModel: 景點頁面取得景點資訊，透過dataBinding顯示資訊
* 網路:   Retrofit
* UI:    Navigation-Fragment、Navigation SafeArgs、Paging3、ViewBinding、DataBinding

* 期望加分項目：Paging3、Flow


![CleanShot 2023-06-01 at 16 25 30](https://github.com/chen1080430/TouristAttraction/assets/32159412/e2edc277-c2a6-494d-958c-ad1fcf07ac64)

截圖：

![screenshot_sutcase2](https://github.com/chen1080430/TouristAttraction/assets/32159412/bcb4cee3-1d10-41b1-bd23-2620765f08e5)

![screenshot_sutcase3](https://github.com/chen1080430/TouristAttraction/assets/32159412/18d4b0a6-55cc-4455-9b51-0f1c511c2f01)

![screenshot_sutcase4](https://github.com/chen1080430/TouristAttraction/assets/32159412/e7380bf8-a87e-48dc-a814-dd991c188d73)

