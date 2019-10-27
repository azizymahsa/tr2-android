package ir.trap.tractor.android.ui.adapters.favoriteCard;

/**
 * Created by Javad.Abadi on 7/18/2018.
 */
public interface ViewPagerAdapterAction
{
    void onFavorite(int position, int cardId);

    void onDelete(int position, int cardId);

    void onEdit(int position, int cardId);

    void onChangePass(int position, int cardId);
}
