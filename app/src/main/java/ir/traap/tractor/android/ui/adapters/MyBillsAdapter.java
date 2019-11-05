package ir.traap.tractor.android.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.getMyBill.Bills;
import ir.traap.tractor.android.ui.fragments.billPay.BillFragment;


/**
 * Created by Javad.Abadi on 9/14/2019.
 */
public class MyBillsAdapter extends RecyclerView.Adapter<MyBillsAdapter.MyViewHolder> {

    private final BillFragment billFragment;
    private List<Bills> billsList;
    private RecyclerView recyclerView;
    private Activity activity;
    private Integer billId;


    public MyBillsAdapter(List<Bills> billsList, RecyclerView recyclerView, Activity activity, BillFragment billFragment) {
        this.billsList = billsList;
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.billFragment = billFragment;
    }

    @NonNull
    @Override
    public MyBillsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_my_bill, parent, false);


        return new MyBillsAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyBillsAdapter.MyViewHolder holder, int position) {
        holder.tvBillName.setText(billsList.get(position).getTitle());
        String billNumber = billsList.get(position).getParameter();
        holder.tvBillNumber.setText(billNumber);
        billId = billsList.get(position).getId();


        holder.llItemMyBill.setOnClickListener(view -> {

            EventBus.getDefault().post(billsList.get(position));
            billFragment.transactionsCollapsed();


        });
       // holder.btnDelete.revertAnimation();

      //  Glide.with(activity).load(billsList.get(position).getLogoBill()).into(holder.ivBillLogo);


   /*     holder.btnEdit.setOnClickListener(view -> {
            new EditMyBillDialog(activity, mainView, (String parameter, String title, Integer type, Integer billId) -> {

                holder.btnEdit.startAnimation();
                holder.btnEdit.setClickable(false);
                EditMyBillRequest request = new EditMyBillRequest();
                request.setBillId(billId);
                request.setParameter(parameter);
                request.setTitle(title);
                request.setType(type);
                SingletonService.getInstance().editMyBillService().editMyBillsService(new OnServiceStatus<EditMyBillResponse>() {
                    @Override
                    public void onReady(EditMyBillResponse response) {
                        holder.btnEdit.revertAnimation();
                        holder.btnEdit.setClickable(true);
                        if (response.getServiceMessage().getCode() == 200) {
                            holder.tvBillName.setText(title);
                          //  mainView.message("ویرایش با موفقیت ثبت شد");
                        } else {
                            mainView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
                        }

                    }

                    @Override
                    public void onError(String message) {
                        mainView.onError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
                        holder.btnEdit.setClickable(true);
                        holder.btnEdit.revertAnimation();

                    }
                }, request);

            }, billsList.get(position).getParameter(), billsList.get(position).getType()
                    , billsList.get(position).getBillId(),billsList.get(position).getTitle()).show(activity.getFragmentManager(), "editBill");
        });*/

      /*  holder.btnDelete.setOnClickListener(view -> {

            new ConfirmDeleteDialog(activity, "آیا از حذف قبض "
                    + billNumber
                    + " اطمینان دارید؟",
                    () -> {
                        holder.btnDelete.startAnimation();
                        holder.btnDelete.setClickable(false);
                        DeleteMyBillRequest deleteMyBillRequest = new DeleteMyBillRequest();
                        deleteMyBillRequest.setBillId( billsList.get(position).getBillId());

                        SingletonService.getInstance().deleteMyBillService().deleteMyBillService(new OnServiceStatus<DeleteMyBillResponse>() {
                            @Override
                            public void onReady(DeleteMyBillResponse response) {
                                if (response.getServiceMessage().getCode() == 200) {
                                    billsList.remove(position);
                                    recyclerView.removeViewAt(position);
                                    notifyDataSetChanged();
                                } else {
                                    mainView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
                                }

                            }

                            @Override
                            public void onError(String message) {
                                mainView.onError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                            }
                        }, deleteMyBillRequest);
                    }, true).show(activity.getFragmentManager(), "deleteCard");

        });*/


    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBillNumber, tvBillName;
        RelativeLayout llItemMyBill;
        ImageView ivBillLogo;
      //  CircularProgressButton btnDelete, btnEdit;


        private MyViewHolder(View convertView) {
            super(convertView);
            tvBillNumber = convertView.findViewById(R.id.tvBillNumber);
            ivBillLogo = convertView.findViewById(R.id.ivBillLogo);
            tvBillName = convertView.findViewById(R.id.tvBillName);
            llItemMyBill = convertView.findViewById(R.id.llItemMyBill);
           // btnDelete = convertView.findViewById(R.id.btnDelete);
          //  btnEdit = convertView.findViewById(R.id.btnEdit);

        }
    }

}