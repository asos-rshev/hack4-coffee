//
//  ItemTuneViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 01/12/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

class ItemTuneViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {

    var item: Item!
    lazy var variant: NameAndPrice? = {
        return self.item.sizes.first
    }()

    @IBOutlet var heroImageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var variantLabel: UILabel!
    @IBOutlet var proceedButton: UIButton!

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

    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return item.sizes.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ItemSizeCollectionViewCell", for: indexPath) as! ItemSizeCollectionViewCell
        return cell
    }


}
