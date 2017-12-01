//
//  ItemsListViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

final class ItemsListViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {

    var selectedCategory: Category!

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return selectedCategory.items.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let item = selectedCategory.items[indexPath.item]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ItemCollectionViewCell", for: indexPath) as! ItemCollectionViewCell
        cell.itemTitleLabel.text = item.name
        cell.itemPriceLabel.text = item.sizes.map({ "£\($0.price)" }).reduce("", { "\($0!) \($1)" })
        return cell
    }

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let item = selectedCategory.items[indexPath.item]
        let vc = storyboard!._itemTune
        vc.item = item
        navigationController?.pushViewController(vc, animated: true)
    }
}
