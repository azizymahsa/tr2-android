package com.traap.traapapp.ui.adapters.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;

/**
 * Created by MahtabAzizi on 5/5/2020.
 */
public class SurveyDetailRadioGroupAdapter extends RecyclerView.Adapter<SurveyDetailRadioGroupAdapter.SurveyDetailViewHolder>{

   // List<Modal_Product_List> prescriptionProducts;
   public int mSelectedItem = -1;


   /* public SurveyDetailRadioGroupAdapter(List<Modal_Product_List> prescriptionListProd) {

        if (prescriptionListProd == null) {
            throw new IllegalArgumentException(
                    "PrescriptionProductList must not be null");
        }
        this.prescriptionProducts = prescriptionListProd;
    }*/

    public SurveyDetailRadioGroupAdapter() {

      /*  if (prescriptionListProd == null) {
            throw new IllegalArgumentException(
                    "PrescriptionProductList must not be null");
        }
        this.prescriptionProducts = prescriptionListProd;*/
    }

    @Override
    public SurveyDetailViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_item_detail_survey_radio_group,
                        viewGroup,
                        false);
        return new SurveyDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(
            SurveyDetailViewHolder viewHolder, int position) {
      /*  Modal_Product_List modelMedicine = prescriptionProducts.get(position);

               viewHolder.mRadio.setChecked(i == mSelectedItem);
*/
    }

    @Override
    public int getItemCount() {
        //return prescriptionProducts.size();
        return 2;
    }

    public final  class SurveyDetailViewHolder
            extends RecyclerView.ViewHolder {


        RadioButton radioButton;

        public SurveyDetailViewHolder(View itemView) {
            super(itemView);

            radioButton = (RadioButton) itemView.findViewById(R.id.radioButton);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            radioButton.setOnClickListener(clickListener);
        }
    }
}
