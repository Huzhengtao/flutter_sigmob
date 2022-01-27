package sgtv.plugin.flutter_sigmob;

import com.sigmob.windad.WindAdError;
import com.sigmob.windad.rewardedVideo.WindRewardInfo;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    static public Map from(final WindAdError obj) {
        return new HashMap() {{
            put("code", obj.getErrorCode());
            put("message", obj.getMessage());
        }};
    }

    static public Map from(final WindRewardInfo obj) {
        return new HashMap() {{
            put("adType", obj.getAdFormat());
            put("placementId", obj.getPlacementId());
            put("isComplete", obj.isComplete());
        }};
    }
}
