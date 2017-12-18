package android.tom.playground.recommendation;

import android.content.Context;

/**
 * Created by tom.saju on 12/18/2017.
 */

public class RecommendedReadController implements IRecommendedReadController {
    Context context;
    IRecomendedReadView recomendedReadView;

    public RecommendedReadController(Context context, IRecomendedReadView recomendedReadView) {
        this.context = context;
        this.recomendedReadView = recomendedReadView;
    }




}
