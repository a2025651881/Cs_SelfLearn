import java.util.HashMap;
import java.util.Map;


/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final  static  double TOTAL_tile_ullon=-122.2998046875;
    private final  static  double TOTAL_tile_ullat=37.892195547244356;
    private final  static  double TOTAL_tile_lrlon=-122.2119140625;
    private final  static  double TOTAL_tile_lrlat=37.82280243352756;
    private Map<String,Double> params;
    public Rasterer() {
        // YOUR CODE HERE
    }

    // 此处选择了较大 边 的比例,使得完全显示
    public  double computeExpectK(){
        double k_w=(params.get("lrlon")-params.get("ullon"))/params.get("w");
        double K_h=(params.get("ullat")-params.get("lrlat"))/params.get("h");
        return  Math.min(K_h,k_w);
    }
    public void choosePic(double k,Map<String, Object> results){
        int depth;
        for(depth=0;depth<7;depth++){
            double pic_i_lonk=(TOTAL_tile_lrlon-TOTAL_tile_ullon)/Math.pow(2,depth)/256;
            double pic_i_latk=(TOTAL_tile_ullat-TOTAL_tile_lrlat)/Math.pow(2,depth)/256;
            // 竖直精度 or 水平精度达到所需
            if(pic_i_lonk < k || pic_i_latk < k){
                break;
            }
        }
        results.put("depth",depth);
    }

    public void pic_radius_area(Map<String, Object> results){
        int depth=(Integer) results.get("depth");
        double pic_i_longap=(TOTAL_tile_lrlon-TOTAL_tile_ullon)/Math.pow(2,depth);
        double pic_i_latgap=(TOTAL_tile_ullat-TOTAL_tile_lrlat)/Math.pow(2,depth);
        int startlon= (int) ((params.get("ullon")-TOTAL_tile_ullon)/pic_i_longap);
        int endlon  = (int) ((params.get("lrlon")-TOTAL_tile_ullon)/pic_i_longap);
        int startlat= (int) ((TOTAL_tile_ullat-params.get("ullat"))/pic_i_latgap);
        int endlat  = (int) ((TOTAL_tile_ullat-params.get("lrlat"))/pic_i_latgap);
        double raster_ul_lon=TOTAL_tile_ullon+startlon*pic_i_longap;
        double raster_lr_lon=TOTAL_tile_ullon+(endlon+1)*pic_i_longap;
        double raster_lr_lat=TOTAL_tile_lrlat+startlat*pic_i_latgap;
        double raster_ul_lat=TOTAL_tile_lrlat+(endlon+1)*pic_i_latgap;
        //添加至 results
        results.put("raster_ul_lon",raster_ul_lon);
        results.put("raster_lr_lon",raster_lr_lon);
        results.put("raster_lr_lat",raster_lr_lat);
        results.put("raster_ul_lat",raster_ul_lat);

        // 计算文件名
        String[][] files=new String[endlat-startlat+1][endlon-startlon+1];
        for(int i=startlat;i<=endlat;i++){
            for(int j=startlon;j<=endlon;j++){
                String s="d"+depth+"_x"+j+"_y"+i+".png";
                files[i-startlat][j-startlon]=s;
            }
        }
        results.put("render_grid",files);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        this.params=params;
        Map<String, Object> results = new HashMap<>();
        // 计算深度
        choosePic(computeExpectK(),results);
        // 计算所需图片 和 地域区间
        pic_radius_area(results);
        //success
        Boolean status=true;
        results.put("query_success",status);
        System.out.println(results);
        return results;
    }

}
