package net.tnemc.core.account.holdings.handlers;

/*
 * The New Economy
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.tnemc.core.account.Account;
import net.tnemc.core.account.PlayerAccount;
import net.tnemc.core.account.holdings.HoldingsEntry;
import net.tnemc.core.account.holdings.HoldingsHandler;
import net.tnemc.core.currency.Currency;
import net.tnemc.core.currency.CurrencyType;
import net.tnemc.core.currency.type.ExperienceType;
import net.tnemc.core.utils.Identifier;
import net.tnemc.plugincore.core.utils.Experience;

import java.math.BigDecimal;
import java.util.Optional;

import static net.tnemc.core.EconomyManager.EXPERIENCE;

/**
 * ExperienceHandler
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class ExperienceHandler implements HoldingsHandler {

  /**
   * The identifier for this handler.
   *
   * @return The identifier that represents this handler.
   */
  @Override
  public Identifier identifier() {

    return EXPERIENCE;
  }

  /**
   * Used to determine if this handler may be used for the specified {@link CurrencyType}.
   *
   * @param type The currency type.
   *
   * @return True if it supports the currency type, otherwise false.
   */
  @Override
  public boolean supports(final Currency currency, final CurrencyType type) {

    return (type instanceof ExperienceType);
  }

  /**
   * Used to set the holdings for a specific account.
   *
   * @param account  The account.
   * @param region   The name of the region involved. This is usually a world, but could be
   *                 something else such as a world guard region name/identifier.
   * @param currency The instance of the currency to use.
   * @param type     The currency type.
   * @param amount   The amount to set the player's holdings to.
   *
   * @return True if the holdings have been set, otherwise false.
   */
  @Override
  public boolean setHoldings(final Account account, final String region, final Currency currency, final CurrencyType type, final BigDecimal amount) {

    account.getWallet().setHoldings(new HoldingsEntry(region, currency.getUid(), amount, identifier()));
    if(account.isPlayer() && ((PlayerAccount)account).isOnline()) {
      Experience.setExperience(((PlayerAccount)account).getPlayer().get(), amount.intValueExact());
    }
    return true;
  }

  /**
   * Used to get the holdings for a specific account from this handler.
   *
   * @param account  The Account.
   * @param region   The name of the region involved. This is usually a world, but could be
   *                 something else such as a world guard region name/identifier.
   * @param currency The instance of the currency to use.
   * @param type     The currency type.
   *
   * @return The holdings for the specific account.
   */
  @Override
  public HoldingsEntry getHoldings(final Account account, final String region, final Currency currency, final CurrencyType type) {

    if(!account.isPlayer() || !((PlayerAccount)account).isOnline()) {
      //Offline players/non-players have their balances saved to their wallet so check it.
      final Optional<HoldingsEntry> holdings = account.getWallet().getHoldings(region,
                                                                               currency.getUid(),
                                                                               EXPERIENCE
                                                                              );

      return holdings.orElseGet(()->new HoldingsEntry(region,
                                                      currency.getUid(),
                                                      BigDecimal.ZERO,
                                                      EXPERIENCE
      ));
    }

    final BigDecimal amount = Experience.getExperienceAsDecimal(((PlayerAccount)account).getPlayer().get());
    final HoldingsEntry entry = new HoldingsEntry(region, currency.getUid(), amount, EXPERIENCE);

    account.getWallet().setHoldings(entry);
    return entry;
  }
}
