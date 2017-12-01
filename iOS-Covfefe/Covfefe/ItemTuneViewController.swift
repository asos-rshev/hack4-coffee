//
//  ItemTuneViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 01/12/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit
import FirebaseAuth
import FirebaseAuthUI

class ItemTuneViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {

    var item: Item!
    lazy var variant: NameAndPrice? = {
        return self.item.sizes.first
    }()

    @IBOutlet var heroImageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var variantLabel: UILabel!
    @IBOutlet var proceedButton: UIButton!
    @IBOutlet var spinnerView: UIActivityIndicatorView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = item.name
        updateVariant()

    }

    func updateVariant() {
        variantLabel.text = variant?.name

        let proceedTitle = "PROCEED (total: £\(variant?.price ?? 999))"
        proceedButton.setTitle(proceedTitle, for: .normal)
    }

    @IBAction func didTapProceedButton() {
        proceedButton.isEnabled = false
        proceedButton.setTitle("", for: .disabled)
        spinnerView.startAnimating()

        let id = Int(Date().timeIntervalSince1970 * 1000)
        let dictionary: NSDictionary = [
            "id": id,
            "inProgress": 0,
            "items": [[
                "count": 1,
                "milky": item.milky,
                "name": item.name,
                "ready": false,
                "size": variant!.name,
                "type": item.type,
                "unitPrice": variant!.price
            ]],
            "name": FUIAuth.defaultAuthUI()!.auth!.currentUser!.displayName!,
            "totalPrice": variant!.price
        ]

        Singleton.shared.databaseModel.firebaseRef.child("orders").child("\(id)").setValue(dictionary) { [weak self] (error, ref) in

            if let error = error {
                let alertVC = UIAlertController(title: "Error", message: "\(error)", preferredStyle: .alert)
                let okAction = UIAlertAction(title: "Mkay", style: .cancel, handler: { (_) in
                    self?.navigationController?.popToRootViewController(animated: true)
                })
                alertVC.addAction(okAction)
                self?.present(alertVC, animated: true, completion: nil)
            } else {
                let vc = UIStoryboard(name: "OrderConfirmationScreen", bundle: nil).instantiateInitialViewController()!
                self?.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return item.sizes.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let size = item.sizes[indexPath.item]
        let isSelected = size.name == variant?.name

        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ItemSizeCollectionViewCell", for: indexPath) as! ItemSizeCollectionViewCell

        cell.selectionView.isHidden = !isSelected
        cell.itemTitleLabel.text = size.name.uppercased()
        cell.itemPriceLabel.text = "£\(size.price)"

        return cell
    }

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let size = item.sizes[indexPath.item]
        variant = size
        updateVariant()
        collectionView.reloadData()
    }
}
