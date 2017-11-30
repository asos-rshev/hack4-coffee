//
//  CategoriesViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

final class CategoriesViewController: UIViewController {

    @IBOutlet var firstCategoryLabel: UILabel!
    @IBOutlet var secondCategoryLabel: UILabel!

    @IBAction func didTapFirstCategory(_ sender: UITapGestureRecognizer) {
        pushItemsListViewController(category: 0)
    }

    @IBAction func didTapSecondCategory(_ sender: UITapGestureRecognizer) {
        pushItemsListViewController(category: 1)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        firstCategoryLabel.text = Singleton.shared.databaseModel.menu[0].name
        secondCategoryLabel.text = Singleton.shared.databaseModel.menu[1].name
    }

    private func pushItemsListViewController(category: Int) {
        let itemsListViewController = storyboard!._itemsList
        itemsListViewController.selectedCategory = category
        navigationController?.pushViewController(itemsListViewController, animated: true)
    }
}
